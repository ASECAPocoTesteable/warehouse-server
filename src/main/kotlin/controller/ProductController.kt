package controller

import model.Product
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import service.ProductService
import util.ProductDTO
import java.util.*

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @PostMapping("/add")
    fun createProduct(@RequestBody product: ProductDTO): ResponseEntity<Product> {
        try {
            val createdProduct = productService.createProduct(product)
            return ResponseEntity.ok(createdProduct)
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: UUID): ResponseEntity<Product> {
        try {
            val product = productService.getProduct(id)
            return product.let { ResponseEntity.ok().body(it) }
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun updateProduct(@RequestBody product: ProductDTO): ResponseEntity<Product> {
        try {
            val updatedProduct = productService.updateProduct(product)
            return ResponseEntity.ok(updatedProduct)
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: UUID): ResponseEntity<Void> {
        try {
            productService.deleteProduct(id)
            return ResponseEntity.noContent().build()
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable productDTO: ProductDTO): ResponseEntity<Product> {
        try {
            val product = productService.getProductById(productDTO.id)
            return ResponseEntity.of(Optional.ofNullable(product))
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }
}
