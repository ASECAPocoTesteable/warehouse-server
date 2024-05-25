package service

import model.Stock
import org.springframework.stereotype.Service
import repository.ProductRepository
import repository.StockRepository
import java.util.UUID

@Service
class StockService(private val productRepository: ProductRepository, private val stockRepository: StockRepository) {

    fun getStock(): List<Stock>{
        return stockRepository.getStock()
    }
    fun getStockByProductID(id: UUID) : Stock{
        return stockRepository.getStockByProductID(id)
    }
    fun updateStock(id: UUID, quantity: Int) : Stock{
         val stock = stockRepository.getStockByProductID(id)
            stock.quantity = quantity
            stockRepository.save(stock)
        return stock
    }
    fun deleteStock(id: UUID) {
         stockRepository.deleteById(id)
    }
    fun createStock(quantity: Int, productId: UUID) : Stock{
        val product = productRepository.findById(productId) ?: throw RuntimeException("Product not found")
        val stock = Stock(quantity = quantity , product = product)
        stockRepository.save(stock)
        return stock
    }
}