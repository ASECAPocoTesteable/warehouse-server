package com.aseca.warehouse.service

import com.aseca.warehouse.model.*
import com.aseca.warehouse.repository.OrderProductRepository
import com.aseca.warehouse.repository.OrderRepository
import com.aseca.warehouse.repository.ProductRepository
import com.aseca.warehouse.util.OrderDTO
import com.aseca.warehouse.util.OrderProductDTO
import com.aseca.warehouse.util.ProductStockRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val stockService: StockService,
    private val webClient: WebClient,
    private val orderProductRepository: OrderProductRepository
) {

    fun createOrder(productStockRequestDto: ProductStockRequestDto): Boolean {
        val isStockAvailable = stockService.checkStock(productStockRequestDto)
        if (!isStockAvailable) {
            throw IllegalArgumentException("Insufficient stock")
        }

        val order = Order(status = STATUS.PENDING)
        val savedOrder = orderRepository.save(order)
        val orderProducts = productStockRequestDto.productList.map {
            createOrderProduct(OrderProductDTO(it.productId, it.quantity), savedOrder)
        }
        orderProductRepository.saveAll(orderProducts)
        return true
    }

    @Transactional
    fun createOrderCheck(orderDTO: OrderDTO): Boolean {
        val order = Order(status = STATUS.PENDING)
        try {
            order.orderProducts = orderDTO.orderProducts.map { createOrderProduct(it, order) }.toMutableList()
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
        val order = orderRepository.findById(orderDTO.id).orElseThrow { NoSuchElementException("Order not found") }
        order.status = orderDTO.status
        order.orderProducts = orderDTO.orderProducts.map { createOrderProduct(it, order) }.toMutableList()
        val savedOrder = orderRepository.save(order)
        return OrderDTO(
            savedOrder.id,
            savedOrder.status,
            savedOrder.orderProducts.map {
                OrderProductDTO(it.product.id, it.quantity)
            }
        )
    }

    fun notifyOrderReadyForPickup(id: Long): Mono<String> {
        return Mono.fromCallable { orderRepository.findById(id) }
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap { optionalOrder ->
                optionalOrder.map { order ->
                    verifyStatusChange(order.status, STATUS.READY_FOR_PICKUP)
                    order.status = STATUS.READY_FOR_PICKUP
                    Mono.fromCallable { orderRepository.save(order) }
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMap { savedOrder ->
                            sendNotificationToControlTower(savedOrder)
                                .doOnError(Throwable::printStackTrace)
                                .flatMap { success ->
                                    if (success == "true") {
                                        Mono.just("Order status updated")
                                    } else {
                                        Mono.error(Exception("Failed to notify status update"))
                                    }
                                }
                        }
                }.orElseGet {
                    Mono.error(NoSuchElementException("Order not found"))
                }
            }
            .onErrorResume { throwable ->
                Mono.error(Exception("Failed to update order due to: ${throwable.message}", throwable))
            }
    }

    fun getAllOrders(): List<OrderDTO> {
        return orderRepository.findAll().map { order ->
            OrderDTO(
                order.id,
                order.status,
                order.orderProducts.map {
                    OrderProductDTO(it.product.idProduct, it.quantity)
                }
            )
        }
    }

    private fun verifyStatusChange(orderStatus: STATUS, status: STATUS) {
        when (status) {
            STATUS.READY_FOR_PICKUP -> {
                if (orderStatus == STATUS.PICKED_UP || orderStatus == STATUS.READY_FOR_PICKUP) {
                    throw Exception("Cannot change from status $orderStatus to $status")
                }
            }

            STATUS.PICKED_UP -> {
                if (orderStatus != STATUS.READY_FOR_PICKUP) {
                    throw Exception("Cannot change from status $orderStatus to $status")
                }
            }

            else -> {
                throw Exception("Invalid status")
            }

        }
    }

    private fun sendNotificationToControlTower(order: Order): Mono<String> {
        val url = "http://controltowerpt:8080/warehouse/order/ready?orderId=${order.id}"

        return webClient.put()
            .uri(url)
            .retrieve()
            .bodyToMono(String::class.java)
            .onErrorResume { throwable ->
                Mono.error(Exception("Failed to notify status update: ${throwable.message}", throwable))
            }
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
                OrderProductDTO(it.product.idProduct, it.quantity)
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
        stockService.reduceProductStock(product.idProduct, orderProductDTO.quantity)
        return OrderProduct(order = order, product = product, quantity = orderProductDTO.quantity)
    }
}
