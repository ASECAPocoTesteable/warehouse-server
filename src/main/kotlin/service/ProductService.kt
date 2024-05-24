package service

import model.Product
import org.springframework.stereotype.Service
import repository.ProductRepository
import java.util.*

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun createProduct(product: Product): Product {
        return productRepository.save(product)
    }

    fun getProduct(id: UUID): Product? {
        return productRepository.findById(id)
    }

    fun updateProduct(product: Product): Product {
        return productRepository.save(product)
    }

    fun deleteProduct(id: UUID) {
        productRepository.deleteById(id)
    }

    fun findByName(name: String): Product? {
        return productRepository.findByName(name)
    }
}