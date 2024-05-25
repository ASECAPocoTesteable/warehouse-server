package controller

import model.Stock
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import service.StockService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import util.stockDTO
import java.util.UUID

@RestController
@RequestMapping("/stock")
class StockController(private val stockService: StockService) {

    @PostMapping("/add")
    fun createStock(@RequestBody stock: stockDTO.StockDTO): ResponseEntity<Stock> {
       try {
           val createdStock = stockService.createStock(stock.quantity, stock.productID)
           return ResponseEntity.ok(createdStock)
       }
        catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/get")
    fun getStock(): ResponseEntity<List<Stock>> {
      try {
          val stocks = stockService.getStock()
          return ResponseEntity.ok(stocks)
      }
        catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/get/{id}")
    fun getStockByProductID(@PathVariable id: UUID): ResponseEntity<Stock> {
     try {
         val stock = stockService.getStockByProductID(id)
         return ResponseEntity.ok(stock)
     }
        catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/update/{id}/{quantity}")
    fun updateStock(@PathVariable id: UUID, @PathVariable quantity: Int): ResponseEntity<Stock> {
        try {
            val updatedStock = stockService.updateStock(id, quantity)
            return ResponseEntity.ok(updatedStock)
        }
        catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteStock(@PathVariable id: UUID): ResponseEntity<Void> {
      try {
          stockService.deleteStock(id)
          return ResponseEntity.noContent().build()
      }
        catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }
}