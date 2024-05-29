package controller

import model.Stock
import service.StockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import util.StockDTO
import java.util.UUID
import util.ProductStockRequestDto

@RestController
@RequestMapping("/stocks")
class StockController(@Autowired private val stockService: StockService) {

    @PutMapping("/{id}")
    fun updateStock(@PathVariable id: UUID, @RequestBody stockDTO: StockDTO): ResponseEntity<Stock>{
        return try {
            ResponseEntity.ok(stockService.updateStock(id, stockDTO))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{id}")
    fun getStockById(@PathVariable id: UUID): ResponseEntity<Stock>{
        return try {
            ResponseEntity.ok(stockService.getStockById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/check")
    fun checkStock(@RequestBody productStockRequestDto : ProductStockRequestDto): ResponseEntity<Boolean> {
        return try {
            ResponseEntity.ok(stockService.checkStock(productStockRequestDto))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/product/{productId}/{warehouseId}")
    fun getStockByProductAndWarehouse(@PathVariable productId: UUID, @PathVariable warehouseId: UUID): ResponseEntity<Int> =
       try {
              ResponseEntity.ok(stockService.getStockByProductAndWarehouse(productId, warehouseId))
         } catch (e: NoSuchElementException) {
              ResponseEntity.notFound().build()
       }
}
