package controller

import com.fasterxml.jackson.databind.ObjectMapper
import model.Product
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import service.ProductService
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var productService: ProductService

    private val objectMapper = ObjectMapper()

    private lateinit var product: Product

    @BeforeEach
    fun setup() {
        product = Product(UUID.randomUUID(), "Test Product")
    }

    @Test
    fun `should create product`() {
        Mockito.`when`(productService.createProduct(product)).thenReturn(product)

        mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
        ).andExpect(status().isOk)
    }

    @Test
    fun `should get product by id`() {
        Mockito.`when`(productService.getProduct(product.id)).thenReturn(Optional.of(product))

        mockMvc.perform(
            get("/products/${product.id}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
    }

    @Test
    fun `should update product`() {
        Mockito.`when`(productService.updateProduct(product)).thenReturn(product)

        mockMvc.perform(
            put("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
        ).andExpect(status().isOk)
    }

    @Test
    fun `should delete product`() {
        Mockito.doNothing().`when`(productService).deleteProduct(product.id)

        mockMvc.perform(
            delete("/products/${product.id}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent)
    }

    @Test
    fun `should find product by name`() {
        Mockito.`when`(productService.findByName(product.name)).thenReturn(product)

        mockMvc.perform(
            get("/products/name/${product.name}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
    }
}