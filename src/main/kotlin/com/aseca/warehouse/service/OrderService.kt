package com.aseca.warehouse.service

import com.aseca.warehouse.model.*
import com.aseca.warehouse.repository.OrderRepository
import com.aseca.warehouse.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.aseca.warehouse.util.OrderDTO
import com.aseca.warehouse.util.OrderProductDTO

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val stockService: StockService
) {

    @Transactional
    fun createOrder(orderDTO: OrderDTO): OrderDTO {
        val order = Order(status = STATUS.PENDING)
        val savedOrder = orderRepository.save(order)
        savedOrder.orderProducts = orderDTO.orderProducts.map { createOrderProduct(it, savedOrder) }
        return OrderDTO(
            savedOrder.id,
            savedOrder.status,
            savedOrder.orderProducts.map {
                OrderProductDTO(it.product.id, it.quantity)
            }
        )
    }

    @Transactional
    fun createOrderCheck(orderDTO: OrderDTO): Boolean {
        val order = Order(status = STATUS.PENDING)
        try {
            order.orderProducts = orderDTO.orderProducts.map { createOrderProduct(it, order) }
            orderRepository.save(order)
            return true
        } catch (e: IllegalArgumentException) {
            return false
        }
    }


    @Transactional
    fun updateOrderStatus(id: Long, status: STATUS): Order {
        val order = orderRepository.findById(id).orElseThrow { NoSuchElementException("Order not found") }
        order.status = status
        return orderRepository.save(order)
    }

    @Transactional
    fun updateOrder(orderDTO: OrderDTO): OrderDTO {
        val order = orderRepository.findById(orderDTO.id, ).orElseThrow { NoSuchElementException("Order not found") }
        order.status = orderDTO.status
        order.orderProducts = orderDTO.orderProducts.map { createOrderProduct(it, order) }
        return OrderDTO(
            order.id,
            order.status,
            order.orderProducts.map {
                OrderProductDTO(it.product.id, it.quantity)
            }
        )
    }

    @Transactional
    fun deleteOrder(id: Long) = orderRepository.deleteById(id)

    @Transactional(readOnly = true)
    fun getOrderById(id: Long): OrderDTO {
        val order = orderRepository.findById(id).orElseThrow { NoSuchElementException("Order not found") }
        return OrderDTO(
            order.id,
            order.status,
            order.orderProducts.map {
                OrderProductDTO(it.product.id, it.quantity)
            }
        )
    }

    fun getOrderStatus(id: Long): String? {
        val order = orderRepository.findById(id).orElseThrow { NoSuchElementException("Order not found") }
        return order.status.name
    }

    private fun createOrderProduct(orderProductDTO: OrderProductDTO, order: Order): OrderProduct {
        val product = productRepository.findById(orderProductDTO.productId)
            .orElseThrow() { NoSuchElementException("Product not found") }
        stockService.reduceProductStock(product.id, orderProductDTO.quantity)
        return OrderProduct(order = order, product = product, quantity = orderProductDTO.quantity)
    }
}
