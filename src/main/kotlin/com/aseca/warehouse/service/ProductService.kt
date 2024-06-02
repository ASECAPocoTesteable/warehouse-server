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
    fun createProduct(productDTO: ProductDTO): Product {
        val product = Product(name = productDTO.name)
        val stock = Stock(quantity = productDTO.stockQuantity, product = product)

        stockRepository.save(stock)
        return productRepository.save(product)
    }

    @Transactional
fun updateProduct(productDTO: ProductDTO): Product {
    val product = productRepository.findById(productDTO.id).orElseThrow() { NoSuchElementException("Product not found") }
    product.name = productDTO.name
    if (product.stock == null) {
        product.stock = Stock(productDTO.stockQuantity, product)
    } else {
        product.stock?.quantity = productDTO.stockQuantity
    }
    return productRepository.save(product)
}
    fun getAllProducts(): List<Product> = productRepository.findAll()

    fun getProductByName(name: String): List<ProductDTO> {
        return productRepository.findByName(name).map { product ->
            ProductDTO(product.id, product.name, product.stock?.quantity ?: 0)
        }
    }

    @Transactional
    fun deleteProduct(id: Long) = productRepository.deleteById(id)

    @Transactional(readOnly = true)
    fun getProductById(id: Long): Product {
        return productRepository.findById(id).orElseThrow() { NoSuchElementException("Product not found") }
    }

    fun getProduct(id: Long): Product {
        return productRepository.findById(id).orElseThrow() { NoSuchElementException("Product not found") }
    }

}
