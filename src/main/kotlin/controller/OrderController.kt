package controller

import model.Order
import service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import util.OrderDTO
import java.util.UUID

@RestController
@RequestMapping("/orders")
class OrderController(@Autowired private val orderService: OrderService) {

    @PostMapping("/")
    fun createOrder(@RequestBody orderDTO: OrderDTO, warehouseID: UUID): ResponseEntity<Order> {
        return try {
            ResponseEntity.ok(orderService.createOrder(orderDTO))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
    @PostMapping("/check-and-create")
fun checkStockAndCreateOrder(@RequestBody orderDTO: OrderDTO): ResponseEntity<Order> {
    return try {
        ResponseEntity.ok(orderService.checkStockAndCreateOrder(orderDTO))
    } catch (e: IllegalArgumentException) {
        ResponseEntity.badRequest().build()
    } catch (e: NoSuchElementException) {
        ResponseEntity.notFound().build()
    }
}

    @PutMapping("/{id}")
    fun updateOrder(@PathVariable id: UUID, @RequestBody orderDTO: OrderDTO): ResponseEntity<Order> {
        return try {
            ResponseEntity.ok(orderService.updateOrder(orderDTO))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable id: UUID): ResponseEntity<Void> {
        try {
            orderService.deleteOrder(id)
            return ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: UUID): ResponseEntity<Order> {
        return try {
            ResponseEntity.ok(orderService.getOrderById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
    @GetMapping("/status/{id}")
    fun getOrderStatus(@PathVariable id: UUID): ResponseEntity<String> {
        return try {
            ResponseEntity.ok(orderService.getOrderStatus(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
}
