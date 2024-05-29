package com.aseca.warehouse.controller

import com.aseca.warehouse.model.Warehouse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.aseca.warehouse.service.WarehouseService
import com.aseca.warehouse.util.WarehouseDTO
import java.util.*

@RestController
class WarehouseController(private val warehouseService: WarehouseService) {

    //crud for warehouses

    //create warehouse
    @PostMapping("/warehouse/add")
    fun createWarehouse(@RequestBody warehouse: WarehouseDTO): ResponseEntity<Warehouse> {
        try {
            val createdWarehouse = warehouseService.createWarehouse(warehouse)
            return ResponseEntity.ok(createdWarehouse)
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/warehouse/{id}")
    fun getWarehouse(@PathVariable id: Long): ResponseEntity<Warehouse> {
        try {
            val warehouse = warehouseService.getWarehouse(id)
            return warehouse.let { ResponseEntity.ok().body(it) }
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/warehouse/{id}")
    fun updateWarehouse(@RequestBody warehouse: WarehouseDTO): ResponseEntity<Warehouse> {
        try {
            val updatedWarehouse = warehouseService.updateWarehouse(warehouse)
            return ResponseEntity.ok(updatedWarehouse)
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/warehouse/{id}")
    fun deleteWarehouse(@PathVariable id: Long): ResponseEntity<Void> {
        try {
            warehouseService.deleteWarehouse(id)
            return ResponseEntity.noContent().build()
        } catch (e: RuntimeException) {
            return ResponseEntity.notFound().build()
        }
    }


}