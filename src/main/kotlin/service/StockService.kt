package service

import model.Stock
import repository.StockRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import util.StockDTO
import java.util.UUID

@Service
class StockService(@Autowired private val stockRepository: StockRepository) {

    @Transactional
    fun updateStock(id: UUID, stockDTO: StockDTO): Stock {
        val stock = stockRepository.findById(id).orElseThrow { NoSuchElementException("Stock not found") }
        stock.quantity = stockDTO.quantity
        return stockRepository.save(stock)
    }

    @Transactional(readOnly = true)
    fun getStockById(id: UUID): Stock =
        stockRepository.findById(id).orElseThrow { NoSuchElementException("Stock not found") }

    @Transactional(readOnly = true)
    fun checkStock(productId: UUID): Int {
        val stock = stockRepository.findByProductId(productId)
        return stock.quantity
    }

    @Transactional
    fun createStock(stock: Stock): Stock {
        val existingStock = stockRepository.findByProductId(stock.product.id)
        if (existingStock != null) {
            existingStock.quantity += stock.quantity
            return stockRepository.save(existingStock)
        }
        if (stock.quantity < 0) {
            throw IllegalArgumentException("Quantity should be greater or equal than 0")
        }
        return stockRepository.save(stock)
    }
}
