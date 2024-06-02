package com.aseca.warehouse.service

import com.aseca.warehouse.model.Stock
import com.aseca.warehouse.repository.StockRepository
import com.aseca.warehouse.util.ProductStockRequestDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.aseca.warehouse.util.StockDTO

@Service
class StockService(@Autowired private val stockRepository: StockRepository) {

    @Transactional
    fun updateStock(id: Long, stockDTO: StockDTO): Stock {
        val stock = stockRepository.findById(id).orElseThrow { NoSuchElementException("Stock not found") }
        stock.quantity = stockDTO.quantity
        return stockRepository.save(stock)
    }

    @Transactional(readOnly = true)
    fun getStockById(id: Long): Stock =
        stockRepository.findById(id).orElseThrow { NoSuchElementException("Stock not found") }

    @Transactional(readOnly = true)
    fun checkStock(stockRequest: ProductStockRequestDto): Boolean {
        stockRequest.productList.forEach {
            val stock = stockRepository.findByProductIdAndShopId(it.productId, )
            if (stock.quantity < it.quantity) {
                return false
            }
        }
        return true
    }
    @Transactional
    fun reduceProductStock(productId: Long, quantity: Int) {
        val productStock = stockRepository.findByProductId(productId)
        if (productStock.isEmpty() || productStock[0].quantity < quantity) {
            throw IllegalArgumentException("Not enough stock for product: $productId")
        }
        productStock[0].quantity -= quantity
        stockRepository.save(productStock[0])
    }

    @Transactional
    fun createStock(stock: Stock): Stock {
        val existingStocks = stockRepository.findByProductId(stock.product.id)
        if (existingStocks.isNotEmpty()) {
            throw IllegalArgumentException("Stock already exists")
        }
        return stockRepository.save(stock)
    }

    fun getStockByProductId(productId: Long): Int {
        return stockRepository.findByProductIdAndWarehouseId(productId)
    }
}