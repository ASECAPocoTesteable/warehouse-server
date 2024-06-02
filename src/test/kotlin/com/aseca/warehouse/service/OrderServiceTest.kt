package com.aseca.warehouse.service

import com.aseca.warehouse.model.*
import com.aseca.warehouse.repository.OrderRepository
import com.aseca.warehouse.repository.ProductRepository
import com.aseca.warehouse.util.OrderDTO
import com.aseca.warehouse.util.OrderProductDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import java.util.*

@ExtendWith(MockitoExtension::class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceTest {

    @Mock
    private lateinit var orderRepository: OrderRepository

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var stockService: StockService

    @InjectMocks
    private lateinit var orderService: OrderService

    @Test
    fun `test createOrder`() {
        val orderDTO = OrderDTO(1, STATUS.PENDING, listOf(OrderProductDTO(1, 10)))
        val order = Order(STATUS.PENDING)
        val product = Product("Test Product")

        given(productRepository.findById(anyLong())).willReturn(Optional.of(product))
        given(orderRepository.save(order)).willReturn(order)

        val createdOrder = orderService.createOrder(orderDTO)

        assertEquals(orderDTO.status, createdOrder.status)
    }

    @Test
    fun `test createOrderCheck`() {
        val orderDTO = OrderDTO(1, STATUS.PENDING, listOf(OrderProductDTO(1, 10)))
        val product = Product("Test Product")

        given(productRepository.findById(anyLong())).willReturn(Optional.of(product))

        val result = orderService.createOrderCheck(orderDTO)

        assertEquals(true, result)
    }

    @Test
    fun `test updateOrderStatus`() {
        val order = Order(STATUS.PENDING)
        order.id = 1

        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order))
        given(orderRepository.save(order)).willReturn(order)

        val updatedOrder = orderService.updateOrderStatus(1, STATUS.PICKED_UP)

        assertEquals(STATUS.PICKED_UP, updatedOrder.status)
    }

    @Test
    fun `test updateOrderStatus with non-existing id`() {
        given(orderRepository.findById(anyLong())).willReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            orderService.updateOrderStatus(1, STATUS.PICKED_UP)
        }
    }

    @Test
    fun `test getOrderById`() {
        val order = Order(STATUS.PENDING)
        order.id = 1

        given(orderRepository.findById(1)).willReturn(Optional.of(order))

        val foundOrder = orderService.getOrderById(1)

        assertEquals(1, foundOrder.id)
    }

    @Test
    fun `test getOrderById with non-existing id`() {
        given(orderRepository.findById(anyLong())).willReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            orderService.getOrderById(1)
        }
    }

    @Test
    fun `test getOrderStatus`() {
        val order = Order(STATUS.PENDING)
        order.id = 1

        given(orderRepository.findById(1)).willReturn(Optional.of(order))

        val foundStatus = orderService.getOrderStatus(1)
        val expectedStatus = "PENDING"
        assertEquals(expectedStatus, foundStatus)
    }

    @Test
    fun `test updateOrder`() {
        val order = Order(STATUS.PENDING)
        order.id = 1
        val product = Product("Test Product")
        product.id = 1
        val orderDTO = OrderDTO(1, STATUS.PENDING, listOf(OrderProductDTO(1, 10)))

        given(orderRepository.findById(1)).willReturn(Optional.of(order))
        given(productRepository.findById(1)).willReturn(Optional.of(product))
        given(orderRepository.save(order)).willReturn(order)

        val updatedOrder = orderService.updateOrder(orderDTO)

        assertEquals(1, updatedOrder.id)
        assertEquals(STATUS.PENDING, updatedOrder.status)
        assertEquals(1, updatedOrder.orderProducts.size)
        assertEquals(1, updatedOrder.orderProducts[0].productId)
        assertEquals(10, updatedOrder.orderProducts[0].quantity)
    }

    @Test
    fun `test updateOrder with new product`() {
        val order = Order(STATUS.PENDING)
        order.id = 1
        val initialProduct = Product("Initial Product")
        initialProduct.id = 1
        order.orderProducts = mutableListOf(OrderProduct(initialProduct, order, 5))

        val newProduct = Product("New Product")
        newProduct.id = 2
        val orderDTO = OrderDTO(1, STATUS.PENDING, listOf(OrderProductDTO(2, 10)))

        given(orderRepository.findById(1)).willReturn(Optional.of(order))
        given(productRepository.findById(2)).willReturn(Optional.of(newProduct))
        given(orderRepository.save(order)).willReturn(order)

        val updatedOrder = orderService.updateOrder(orderDTO)

        assertEquals(1, updatedOrder.id)
        assertEquals(1, updatedOrder.orderProducts.size)
        assertEquals(2, updatedOrder.orderProducts[0].productId)
        assertEquals(10, updatedOrder.orderProducts[0].quantity)
    }
}
