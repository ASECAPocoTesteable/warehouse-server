package com.aseca.warehouse.service

import com.aseca.warehouse.model.Product
import com.aseca.warehouse.model.Stock
import com.aseca.warehouse.repository.ProductRepository
import com.aseca.warehouse.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.aseca.warehouse.util.ProductDTO

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val stockRepository: StockRepository,
) {

    @Transactional
    fun createProduct(productDTO: ProductDTO): ProductDTO {
        if (productDTO.stockQuantity < 0) {
            throw IllegalArgumentException("Product quantity cannot be negative")
        }

        val product = Product(name = productDTO.name)
        val savedProduct = productRepository.save(product)

        val stock = Stock(quantity = productDTO.stockQuantity, product = savedProduct)
        stockRepository.save(stock)

        return ProductDTO(savedProduct.id, savedProduct.name, stock.quantity)
    }

    @Transactional
    fun updateProduct(productDTO: ProductDTO): ProductDTO {
        if (productDTO.stockQuantity < 0) {
            throw IllegalArgumentException("Product quantity cannot be negative")
        }

        val product = productRepository.findById(productDTO.id).orElseThrow { NoSuchElementException("Product not found") }
        product.name = productDTO.name

        val stock = stockRepository.findByProductId(product.id).first()
        stock.quantity = productDTO.stockQuantity

        stockRepository.save(stock)
        productRepository.save(product)

        return ProductDTO(product.id, product.name, stock.quantity)
    }

    @Transactional
    fun deleteProduct(id: Long) = productRepository.deleteById(id)

    @Transactional(readOnly = true)
    fun getProductById(id: Long): Product {
        return productRepository.findById(id).orElseThrow() { NoSuchElementException("Product not found") }
    }
    fun getProductByName(name: String): List<ProductDTO> {
        return productRepository.findByName(name).map { product ->
            ProductDTO(product.id, product.name, product.stock?.quantity ?: 0)
        }
    }

    fun getProduct(id: Long): ProductDTO {
        val product = productRepository.findById(id).orElseThrow { NoSuchElementException("Product not found") }
        return ProductDTO(product.id, product.name, product.stock?.quantity ?: 0)
    }

    fun getAllProducts(): List<ProductDTO> {
        return productRepository.findAll().map { product ->
            ProductDTO(product.id, product.name, product.stock?.quantity ?: 0)
        }
    }

}
