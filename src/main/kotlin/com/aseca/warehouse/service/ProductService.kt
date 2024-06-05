package com.aseca.warehouse.service

import com.aseca.warehouse.model.Product
import com.aseca.warehouse.model.Stock
import com.aseca.warehouse.repository.ProductRepository
import com.aseca.warehouse.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.aseca.warehouse.util.ProductDTO
import com.aseca.warehouse.util.UpdateProductDTO

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

        val product = Product(name = productDTO.name, idProduct = productDTO.id)
        val stock = Stock(quantity = productDTO.stockQuantity, product = product)

        product.stock = stock // Associate the Stock object with the Product object

        val savedProduct = productRepository.save(product)
        stockRepository.save(stock)

        return ProductDTO(savedProduct.idProduct, savedProduct.name, stock.quantity)
    }

    @Transactional
    fun updateProduct(updateProductDTO: UpdateProductDTO): ProductDTO {
        if (updateProductDTO.addedQuantity < 0) {
            throw IllegalArgumentException("Product quantity cannot be negative")
        }

        val product = productRepository.findByProductId(updateProductDTO.id).orElseThrow { NoSuchElementException("Product not found") }
        val stock = stockRepository.findByProductId(product.idProduct).first()
        stock.quantity += updateProductDTO.addedQuantity

        stockRepository.save(stock)
        productRepository.save(product)

        return ProductDTO(product.idProduct, product.name, stock.quantity)
    }

    @Transactional
    fun deleteProduct(id: Long) = productRepository.deleteById(id)

    @Transactional(readOnly = true)
    fun getProductById(id: Long): Product {
        return productRepository.findByProductId(id).orElseThrow() { NoSuchElementException("Product not found") }
    }
    fun getProductByName(name: String): List<ProductDTO> {
        return productRepository.findByName(name).map { product ->
            ProductDTO(product.idProduct, product.name, product.stock?.quantity ?: 0)
        }
    }

    fun getProduct(id: Long): ProductDTO {
        val product = productRepository.findByProductId(id).orElseThrow { NoSuchElementException("Product not found") }
        return ProductDTO(product.idProduct, product.name, product.stock?.quantity ?: 0)
    }

    fun getAllProducts(): List<ProductDTO> {
        return productRepository.findAll().map { product ->
            ProductDTO(product.idProduct, product.name, product.stock?.quantity ?: 0)
        }
    }

}
