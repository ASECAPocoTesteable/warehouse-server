package service

import model.Product
import model.Stock
import repository.ProductRepository
import repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import repository.WarehouseRepository
import util.ProductDTO
import java.util.UUID

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
        val product = productRepository.findById(productDTO.id) ?: throw NoSuchElementException("Product not found")
        product.name = productDTO.name
        product.stock?.quantity = productDTO.stockQuantity
        return productRepository.save(product)
    }

    @Transactional
    fun deleteProduct(id: UUID) = productRepository.deleteById(id)

    @Transactional(readOnly = true)
    fun getProductById(id: UUID): Product {
        return productRepository.findById(id) ?: throw NoSuchElementException("Product not found")
    }

    fun getProduct(id: UUID): Product {
        return productRepository.findById(id) ?: throw NoSuchElementException("Product not found")
    }

}
