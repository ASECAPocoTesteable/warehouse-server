package com.aseca.warehouse.service

import com.aseca.warehouse.model.Order
import com.aseca.warehouse.model.OrderProduct
import com.aseca.warehouse.repository.OrderRepository
import com.aseca.warehouse.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.aseca.warehouse.repository.WarehouseRepository
import com.aseca.warehouse.util.OrderDTO
import java.util.UUID

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val warehouseRepository: WarehouseRepository
) {

    @Transactional
    fun createOrder(orderDTO: OrderDTO): Order {
        val warehouse = warehouseRepository.findById(orderDTO.warehouseId).orElseThrow { NoSuchElementException("Warehouse not found") }
        val order = Order(
            status = orderDTO.status, warehouse = warehouse, orderProducts = mutableListOf()
        )
        val products = mutableListOf<OrderProduct>()
        for (orderProductDTO in orderDTO.orderProducts) {
            val product = productRepository.findById(orderProductDTO.productId).orElseThrow() { NoSuchElementException("Product not found") }
            if ((product.stock?.quantity ?: 0) < orderProductDTO.quantity) {
                throw IllegalArgumentException("Not enough stock for product: ${product.name}")
            }
            product.stock?.quantity = product.stock?.quantity?.minus(orderProductDTO.quantity) ?: 0
            productRepository.save(product)
            products.add(OrderProduct(order = order, product = product, quantity = orderProductDTO.quantity))
        }
        order.orderProducts = products
        return orderRepository.save(order)
    }

    @Transactional
    fun updateOrder(orderDTO: OrderDTO): Order { //Testear
        val order = orderRepository.findById(orderDTO.id)
            .orElseThrow { NoSuchElementException("Order not found") }
        order.status = orderDTO.status
        order.orderProducts = orderDTO.orderProducts.map {
            OrderProduct(
                order = order,
                product = productRepository.findById(it.productId).orElseThrow() { NoSuchElementException("Product not found") },
                quantity = it.quantity
            )
        }
        return orderRepository.save(order)
    }

    @Transactional
    fun deleteOrder(id: Long) = orderRepository.deleteById(id)

    @Transactional(readOnly = true)
    fun getOrderById(id: Long): Order = orderRepository.findById(id).orElseThrow { NoSuchElementException("Order not found") }
    fun getOrderStatus(id: Long): String? {
        val order = orderRepository.findById(id).orElseThrow { NoSuchElementException("Order not found") }
        return order.status.name
    }
}
