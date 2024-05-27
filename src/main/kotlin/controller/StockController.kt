package controller

import model.Stock
import service.StockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import util.StockDTO
import java.util.UUID

@RestController
@RequestMapping("/stocks")
class StockController(@Autowired private val stockService: StockService) {

    @PutMapping("/{id}")
    fun updateStock(@PathVariable id: UUID, @RequestBody stockDTO: StockDTO): ResponseEntity<Stock> =
        ResponseEntity.ok(stockService.updateStock(id, stockDTO))

    @GetMapping("/{id}")
    fun getStockById(@PathVariable id: UUID): ResponseEntity<Stock> =
        ResponseEntity.ok(stockService.getStockById(id))

    @GetMapping("/check/{productId}")
    fun checkStock(@PathVariable productId: UUID): ResponseEntity<Int> =
        ResponseEntity.ok(stockService.checkStock(productId))
}
