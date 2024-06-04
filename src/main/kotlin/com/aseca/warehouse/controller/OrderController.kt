package com.aseca.warehouse.controller

import com.aseca.warehouse.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.aseca.warehouse.util.OrderDTO
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/order")
class OrderController(private val orderService: OrderService) {

    @PutMapping("/ready-for-pickup/{id}")
    fun notifyOrderReady(@PathVariable id: Long): Mono<ResponseEntity<String>> {
        return orderService.notifyOrderReadyForPickup(id)
            .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @PostMapping("/create")
    fun createOrder(@RequestBody orderDTO: OrderDTO): ResponseEntity<OrderDTO> {
        return try {
            ResponseEntity.ok(orderService.createOrder(orderDTO))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/orders/{id}")
    fun updateOrder(@PathVariable id: Long, @RequestBody orderDTO: OrderDTO): ResponseEntity<OrderDTO> {
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
    fun getOrderById(@PathVariable id: Long): ResponseEntity<OrderDTO> {
        return try {
            ResponseEntity.ok(orderService.getOrderById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
    @GetMapping("/all")
    fun getAllOrders(): ResponseEntity<List<OrderDTO>> {
        return ResponseEntity.ok(orderService.getAllOrders())
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
