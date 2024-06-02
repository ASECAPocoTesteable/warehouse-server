package com.aseca.warehouse.controller

import com.aseca.warehouse.model.Product
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.aseca.warehouse.service.ProductService
import com.aseca.warehouse.util.ProductDTO
import java.util.*

@RestController
@RequestMapping("/product")
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
    fun getProduct(@PathVariable id: Long): ResponseEntity<Product> {
        try {
            val product = productService.getProduct(id)
            return product.let { ResponseEntity.ok().body(it) }
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/all")
    fun getAllProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
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
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        try {
            productService.deleteProduct(id)
            return ResponseEntity.noContent().build()
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String): ResponseEntity<List<ProductDTO>> {
        try {
            val product : List<ProductDTO> = productService.getProductByName(name)
            return ResponseEntity.of(Optional.ofNullable(product))
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }
}
