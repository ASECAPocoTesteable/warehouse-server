package com.aseca.warehouse.controller

import com.aseca.warehouse.model.Stock
import com.aseca.warehouse.service.StockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.aseca.warehouse.util.StockDTO
import com.aseca.warehouse.util.ProductStockRequestDto

@RestController
class StockController(@Autowired private val stockService: StockService) {

    @PutMapping("/stocks/{id}")
    fun updateStock(@PathVariable id: Long, @RequestBody stockDTO: StockDTO): ResponseEntity<Stock>{
        return try {
            ResponseEntity.ok(stockService.updateStock(id, stockDTO))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/stocks/{id}")
    fun getStockById(@PathVariable id: Long): ResponseEntity<Stock>{
        return try {
            ResponseEntity.ok(stockService.getStockById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/stocks/check")
    fun checkStock(@RequestBody productStockRequestDto : ProductStockRequestDto): ResponseEntity<Boolean> {
        return try {
            ResponseEntity.ok(stockService.checkStock(productStockRequestDto))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/stocks/product/{productId}/{warehouseId}")
    fun getStockByProductAndWarehouse(@PathVariable productId: Long, @PathVariable warehouseId: Long): ResponseEntity<Int> =
       try {
              ResponseEntity.ok(stockService.getStockByProductAndWarehouse(productId, warehouseId))
         } catch (e: NoSuchElementException) {
              ResponseEntity.notFound().build()
       }
}
