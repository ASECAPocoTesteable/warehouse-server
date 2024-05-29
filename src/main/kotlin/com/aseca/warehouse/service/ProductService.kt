package com.aseca.warehouse.service

import com.aseca.warehouse.model.Product
import com.aseca.warehouse.model.Stock
import com.aseca.warehouse.repository.ProductRepository
import com.aseca.warehouse.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.aseca.warehouse.repository.WarehouseRepository
import com.aseca.warehouse.util.ProductDTO

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val stockRepository: StockRepository,
    private val warehouseRepository: WarehouseRepository
) {

    @Transactional
    fun createProduct(productDTO: ProductDTO): Product {
        val product = Product(name = productDTO.name)
        val warehouse =
            warehouseRepository.findById(productDTO.warehouseId).orElseThrow { NoSuchElementException("Warehouse not found") }
        val stock = Stock(quantity = productDTO.stockQuantity, product = product, warehouse = warehouse)

        stockRepository.save(stock)
        return productRepository.save(product)
    }

    @Transactional
    fun updateProduct(productDTO: ProductDTO): Product { //Tengo dudas del manejo de la inmutabilidad cuando updateo un producto. Por el tema de que hay orderProducts y mas cosas
        val product = productRepository.findById(productDTO.id).orElseThrow() { NoSuchElementException("Product not found") }
        product.name = productDTO.name
        product.stock?.quantity = productDTO.stockQuantity
        return productRepository.save(product)
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
