package service

import org.springframework.stereotype.Service
import repository.ProductRepository

@Service
class StockService(productRepository: ProductRepository) {

    private val productRepository = productRepository

    fun getStock() {
        productRepository.findAll()
    }

}