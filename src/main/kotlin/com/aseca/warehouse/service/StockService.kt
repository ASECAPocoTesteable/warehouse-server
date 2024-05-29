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
            val stock = stockRepository.findByProductIdAndShopId(it.productId, stockRequest.shopId)
            if (stock.quantity < it.quantity) {
                return false
            }
        }
        return true
    }

    @Transactional
    fun createStock(stock: Stock): Stock {
        val existingStocks = stockRepository.findByProductId(stock.product.id)
        val existingStock = existingStocks.find { it.warehouse.id == stock.warehouse.id }

        if (existingStock != null) {
            existingStock.quantity += stock.quantity
            return stockRepository.save(existingStock)
        }

        if (stock.quantity < 0) {
            throw IllegalArgumentException("Quantity should be greater or equal than 0")
        }

        return stockRepository.save(stock)
    }

    fun getStockByProductAndWarehouse(productId: Long, warehouseId: Long): Int {
        return stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
    }
}
