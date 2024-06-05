package com.aseca.warehouse.service

import com.aseca.warehouse.model.Product
import com.aseca.warehouse.model.Stock
import com.aseca.warehouse.repository.ProductRepository
import com.aseca.warehouse.repository.StockRepository
import com.aseca.warehouse.util.ProductDTO
import com.aseca.warehouse.util.UpdateProductDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class ProductServiceTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var stockRepository: StockRepository

    @InjectMocks
    private lateinit var productService: ProductService

//    @Test
//    fun `test createProduct`() {
//        val productDTO = ProductDTO(1, "Test Product", 10)
//        val product = Product("Test Product")
//        val stock = Stock(10, product)
//
//        given(productRepository.save(product)).willReturn(product)
//        given(stockRepository.save(stock)).willReturn(stock)
//
//        val createdProduct = productService.createProduct(productDTO)
//
//        assertEquals(productDTO.name, createdProduct.name)
//    }

    @Test
    fun `test getProductById`() {
        val product = Product("Test Product", id = 1)
        product.id = 1

        given(productRepository.findById(anyLong())).willReturn(Optional.of(product))

        val foundProduct = productService.getProductById(1)

        assertEquals(product.name, foundProduct.name)
    }

    @Test
    fun `test getProductById with non-existing id`() {
        given(productRepository.findById(anyLong())).willReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            productService.getProductById(1)
        }
    }

    @Test
    fun `test updateProduct`() {
        val productDTO = ProductDTO(1, "Updated Test Product", 20)
        val updateProductDTO = UpdateProductDTO(1, 10)
        val product = Product("Test Product", id = 1)
        product.id = 1
        val stock = Stock(10, product)

        given(productRepository.findById(1)).willReturn(Optional.of(product))
        given(stockRepository.findByProductId(1)).willReturn(listOf(stock))

        val updatedProduct = productService.updateProduct(updateProductDTO)

        assertEquals(productDTO.stockQuantity, updatedProduct.stockQuantity)
    }


    @Test
    fun `test deleteProduct`() {
        val product = Product("Test Product", id = 1)
        product.id = 1

        productService.deleteProduct(1)

        verify(productRepository).deleteById(1)
    }

    @Test
    fun `test getAllProducts`() {
        val product1 = Product("Test Product 1", id = 1)
        val product2 = Product("Test Product 2", id = 2)

        given(productRepository.findAll()).willReturn(listOf(product1, product2))

        val products = productService.getAllProducts()

        assertEquals(2, products.size)
    }

    @Test
    fun `test getProductByName`() {
        val product = Product("Test Product", id = 1)
        product.id = 1

        given(productRepository.findByName("Test Product")).willReturn(listOf(product))

        val foundProducts = productService.getProductByName("Test Product")

        assertEquals(1, foundProducts.size)
        assertEquals(1, foundProducts[0].id)
    }

    @Test
    fun `test getProduct`() {
        val product = Product("Test Product", id = 1)
        product.id = 1

        given(productRepository.findById(1)).willReturn(Optional.of(product))

        val foundProduct = productService.getProduct(1)

        assertEquals(1, foundProduct.id)
    }
}