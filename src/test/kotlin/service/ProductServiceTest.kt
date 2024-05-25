package service

import model.Category
import model.Product
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import repository.ProductRepository
import java.util.*

@SpringBootTest
class ProductServiceTest {

    @MockBean
    private lateinit var productRepository: ProductRepository

    private lateinit var productService: ProductService

    private lateinit var product: Product

    @BeforeEach
    fun setup() {
        productService = ProductService(productRepository)
        product = Product(UUID.randomUUID(), "Test Product", "Test Description", categoryProducts = List<Category>{ Category(UUID.randomUUID(), "Test Category") })
    }

    @Test
    fun `should create product`() {
        `when`(productRepository.save(product)).thenReturn(product)

        productService.createProduct(product)

        verify(productRepository).save(product)
    }

    @Test
    fun `should get product by id`() {
        `when`(productRepository.findById(product.id)).thenReturn(Optional.of(product))

        productService.getProduct(product.id)

        verify(productRepository).findById(product.id)
    }

    @Test
    fun `should update product`() {
        `when`(productRepository.save(product)).thenReturn(product)

        productService.updateProduct(product)

        verify(productRepository).save(product)
    }

    @Test
    fun `should delete product`() {
        Mockito.doNothing().`when`(productRepository).deleteById(product.id)

        productService.deleteProduct(product.id)

        verify(productRepository).deleteById(product.id)
    }

    @Test
    fun `should find product by name`() {
        `when`(productRepository.findByName(product.name)).thenReturn(product)

        productService.findByName(product.name)

        verify(productRepository).findByName(product.name)
    }
}