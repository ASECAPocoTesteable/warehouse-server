package com.aseca.warehouse.controller

import com.aseca.warehouse.model.Product
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.aseca.warehouse.service.ProductService
import com.aseca.warehouse.util.ProductDTO
import com.aseca.warehouse.util.UpdateProductDTO
import java.util.*

@RestController
@RequestMapping("/product")
class ProductController(private val productService: ProductService) {

    @PostMapping("/add")
    fun createProduct(@RequestBody product: ProductDTO): ResponseEntity<*> {
        try {
            productService.createProduct(product)
            return ResponseEntity.ok("success")
        } catch (e: RuntimeException) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long): ResponseEntity<ProductDTO> {
        try {
            val product = productService.getProduct(id)
            return product.let { ResponseEntity.ok().body(it) }
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/all")
    fun getAllProducts(): ResponseEntity<List<ProductDTO>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
    }

    @PutMapping("/{id}")
    fun updateProduct(@RequestBody product: UpdateProductDTO): ResponseEntity<ProductDTO> {
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
