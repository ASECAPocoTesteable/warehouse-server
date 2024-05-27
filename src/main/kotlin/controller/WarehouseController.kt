package controller

import model.Warehouse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import service.WarehouseService
import util.WarehouseDTO
import java.util.*

@RestController
@RequestMapping("/warehouse")
class WarehouseController(private val warehouseService: WarehouseService) {

    //crud for warehouses

    //create warehouse
    @PostMapping("/add")
    fun createWarehouse(@RequestBody warehouse: WarehouseDTO): ResponseEntity<Warehouse> {
        try {
            val createdWarehouse = warehouseService.createWarehouse(warehouse)
            return ResponseEntity.ok(createdWarehouse)
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{id}")
    fun getWarehouse(@PathVariable id: UUID): ResponseEntity<Warehouse> {
        try {
            val warehouse = warehouseService.getWarehouse(id)
            return warehouse.let { ResponseEntity.ok().body(it) }
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun updateWarehouse(@RequestBody warehouse: WarehouseDTO): ResponseEntity<Warehouse> {
        try {
            val updatedWarehouse = warehouseService.updateWarehouse(warehouse)
            return ResponseEntity.ok(updatedWarehouse)
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteWarehouse(@PathVariable id: UUID): ResponseEntity<Void> {
        try {
            warehouseService.deleteWarehouse(id)
            return ResponseEntity.noContent().build()
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }


}