package com.aseca.warehouse.controller

import com.aseca.warehouse.model.Product
import com.aseca.warehouse.service.ProductService
import com.aseca.warehouse.util.ProductDTO
import com.aseca.warehouse.util.UpdateProductDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.ResponseEntity
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockitoExtension::class)
class ProductControllerTest {

    @Mock
    private lateinit var productService: ProductService

    @InjectMocks
    private lateinit var productController: ProductController

    @Test
    fun `test createProduct`() {
        val productDTO = ProductDTO(1, "Test Product", 10)


        `when`(productService.createProduct(productDTO)).thenReturn(productDTO)

        val createdProduct = productController.createProduct(productDTO)

        assertEquals(ResponseEntity.ok(productDTO), createdProduct)
        verify(productService).createProduct(productDTO)
    }

    @Test
    fun `test getProduct`() {
        val productDTO = ProductDTO(1, "Test Product", 10)

        `when`(productService.getProduct(1)).thenReturn(productDTO)

        val foundProduct = productController.getProduct(1)

        assertEquals(ResponseEntity.ok(productDTO), foundProduct)
        verify(productService).getProduct(1)
    }

    @Test
    fun `test getAllProducts`() {
        val productsDTO = listOf(ProductDTO(1, "Test Product 1", 10), ProductDTO(2, "Test Product 2", 20))

        `when`(productService.getAllProducts()).thenReturn(productsDTO)

        val allProducts = productController.getAllProducts()

        assertEquals(ResponseEntity.ok(productsDTO), allProducts)
        verify(productService).getAllProducts()
    }

    @Test
    fun `test updateProduct`() {
        val productDTO = ProductDTO(1, "Updated Test Product", 20)
        val updateInfoDTO = UpdateProductDTO(1, 10)

        `when`(productService.updateProduct(updateInfoDTO)).thenReturn(productDTO)

        val updatedProduct = productController.updateProduct(updateInfoDTO)

        assertEquals(ResponseEntity.ok(productDTO), updatedProduct)
        verify(productService).updateProduct(updateInfoDTO)
    }

    @Test
    fun `test deleteProduct`() {
        doNothing().`when`(productService).deleteProduct(1)

        val response = productController.deleteProduct(1)

        assertEquals(ResponseEntity.noContent().build<Void>(), response)
        verify(productService).deleteProduct(1)
    }

    @Test
    fun `test findByName`() {
        val productsDTO = listOf(ProductDTO(1, "Test Product", 10), ProductDTO(2, "Test Product", 20))

        `when`(productService.getProductByName("Test Product")).thenReturn(productsDTO)

        val foundProducts = productController.findByName("Test Product")

        assertEquals(ResponseEntity.of(Optional.ofNullable(productsDTO)), foundProducts)
        verify(productService).getProductByName("Test Product")
    }

    @Test
    fun `test findByName with no products found`() {
        val productsDTO = emptyList<ProductDTO>()

        `when`(productService.getProductByName("Test Product")).thenReturn(productsDTO)

        val foundProducts = productController.findByName("Test Product")

        assertEquals(ResponseEntity.of(Optional.ofNullable(productsDTO)), foundProducts)
        verify(productService).getProductByName("Test Product")
    }

    @Test
    fun `test createProduct with exception`() {
        val productDTO = ProductDTO(1, "Test Product", 10)

        `when`(productService.createProduct(productDTO)).thenThrow(RuntimeException::class.java)

        val response = productController.createProduct(productDTO)

        assertEquals(ResponseEntity.notFound().build<ProductDTO>(), response)
    }

    @Test
    fun `test getProduct with exception`() {
        `when`(productService.getProduct(1)).thenThrow(RuntimeException::class.java)

        val response = productController.getProduct(1)

        assertEquals(ResponseEntity.notFound().build<ProductDTO>(), response)
    }

    @Test
    fun `test updateProduct with exception`() {
        val productDTO = ProductDTO(1, "Updated Test Product", 20)
        val updateInfoDTO = UpdateProductDTO(1, 10)

        `when`(productService.updateProduct(updateInfoDTO)).thenThrow(RuntimeException::class.java)

        val response = productController.updateProduct(updateInfoDTO)

        assertEquals(ResponseEntity.notFound().build<ProductDTO>(), response)
    }

    @Test
    fun `test deleteProduct with exception`() {
        doThrow(RuntimeException::class.java).`when`(productService).deleteProduct(1)

        val response = productController.deleteProduct(1)

        assertEquals(ResponseEntity.notFound().build<Void>(), response)
    }

    @Test
    fun `test findByName with exception`() {
        `when`(productService.getProductByName("Test Product")).thenThrow(RuntimeException::class.java)

        val response = productController.findByName("Test Product")

        assertEquals(ResponseEntity.notFound().build<List<ProductDTO>>(), response)
    }
}
