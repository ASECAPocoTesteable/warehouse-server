package controller

import model.Product
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import service.ProductService
import java.util.*

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @PostMapping
    fun createProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val createdProduct = productService.createProduct(product)
        return ResponseEntity.ok(createdProduct)
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: UUID): ResponseEntity<Product> {
        val product = productService.getProduct(id)
        return ResponseEntity.of(product)
    }

    @PutMapping
    fun updateProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val updatedProduct = productService.updateProduct(product)
        return ResponseEntity.ok(updatedProduct)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: UUID): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String): ResponseEntity<Product> {
        val product = productService.findByName(name)
        return ResponseEntity.of(Optional.ofNullable(product))
    }
}