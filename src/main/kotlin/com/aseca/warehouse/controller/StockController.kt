package com.aseca.warehouse.controller

import com.aseca.warehouse.exception.InsufficientStockException
import com.aseca.warehouse.model.Stock
import com.aseca.warehouse.service.StockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.aseca.warehouse.util.StockDTO
import com.aseca.warehouse.util.ProductStockRequestDto

@RestController
@RequestMapping("/stock")
class StockController(@Autowired private val stockService: StockService) {

    @PutMapping("/{id}")
    fun updateStock(@PathVariable id: Long, @RequestBody stockDTO: StockDTO): ResponseEntity<Stock> {
        return try {
            ResponseEntity.ok(stockService.updateStock(id, stockDTO))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{id}")
    fun getStockById(@PathVariable id: Long): ResponseEntity<Stock> {
        return try {
            ResponseEntity.ok(stockService.getStockById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/check")
    fun checkStock(@RequestBody productStockRequestDto: ProductStockRequestDto): ResponseEntity<Boolean> {
        try {
            val isStockAvailable = stockService.checkStock(productStockRequestDto)
            if (!isStockAvailable) {
                throw InsufficientStockException()
            }
            return ResponseEntity.ok(isStockAvailable)
        } catch (e: NoSuchElementException) {
            return ResponseEntity.badRequest().build()
        }
    }
}
