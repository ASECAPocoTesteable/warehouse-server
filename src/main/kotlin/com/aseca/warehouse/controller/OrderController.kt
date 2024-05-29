package com.aseca.warehouse.controller

import com.aseca.warehouse.model.Order
import com.aseca.warehouse.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.aseca.warehouse.util.OrderDTO
import java.util.UUID

@RestController
class OrderController(@Autowired private val orderService: OrderService) {

    @PostMapping("/orders/")
    fun createOrder(@RequestBody orderDTO: OrderDTO, warehouseID: Long): ResponseEntity<Order> {
        return try {
            ResponseEntity.ok(orderService.createOrder(orderDTO))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/orders/{id}")
    fun updateOrder(@PathVariable id: Long, @RequestBody orderDTO: OrderDTO): ResponseEntity<Order> {
        return try {
            ResponseEntity.ok(orderService.updateOrder(orderDTO))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/orders/{id}")
    fun deleteOrder(@PathVariable id: Long): ResponseEntity<Void> {
        try {
            orderService.deleteOrder(id)
            return ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/orders/{id}")
    fun getOrderById(@PathVariable id: Long): ResponseEntity<Order> {
        return try {
            ResponseEntity.ok(orderService.getOrderById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
    @GetMapping("/orders/status/{id}")
    fun getOrderStatus(@PathVariable id: Long): ResponseEntity<String> {
        return try {
            ResponseEntity.ok(orderService.getOrderStatus(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
}
