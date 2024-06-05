package com.aseca.warehouse.controller

import com.aseca.warehouse.model.STATUS
import com.aseca.warehouse.service.OrderService
import com.aseca.warehouse.util.OrderDTO
import com.aseca.warehouse.util.OrderProductDTO
import com.aseca.warehouse.util.ProductStock
import com.aseca.warehouse.util.ProductStockRequestDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
class OrderControllerTest {

    @Mock
    private lateinit var orderService: OrderService

    @InjectMocks
    private lateinit var orderController: OrderController

    @Test
    fun `test createOrder`() {
        val productStockRequestDto = ProductStockRequestDto(listOf(ProductStock(1, 10)))

        `when`(orderService.createOrder(productStockRequestDto)).thenReturn(true)

        val response = orderController.createOrder(productStockRequestDto)

        assertEquals(ResponseEntity.ok(true), response)
    }

    @Test
    fun `test updateOrder`() {
        val orderDTO = OrderDTO(1, STATUS.PENDING, listOf(OrderProductDTO(1, 10)))

        `when`(orderService.updateOrder(orderDTO)).thenReturn(orderDTO)

        val response = orderController.updateOrder(1, orderDTO)

        assertEquals(ResponseEntity.ok(orderDTO), response)
    }

    @Test
    fun `test deleteOrder`() {
        doNothing().`when`(orderService).deleteOrder(1)

        val response = orderController.deleteOrder(1)

        assertEquals(ResponseEntity.noContent().build<Void>(), response)
    }

    @Test
    fun `test getOrderById`() {
        val orderDTO = OrderDTO(1, STATUS.PENDING, listOf(OrderProductDTO(1, 10)))

        `when`(orderService.getOrderById(1)).thenReturn(orderDTO)

        val response = orderController.getOrderById(1)

        assertEquals(ResponseEntity.ok(orderDTO), response)
    }

    @Test
    fun `test getOrderStatus`() {
        `when`(orderService.getOrderStatus(1)).thenReturn(STATUS.PENDING.name)

        val response = orderController.getOrderStatus(1)

        assertEquals(ResponseEntity.ok(STATUS.PENDING.name), response)
    }

    @Test
    fun `test createOrder with IllegalArgumentException`() {
        val productStockRequestDto = ProductStockRequestDto(listOf(ProductStock(1, 10)))

        `when`(orderService.createOrder(productStockRequestDto)).thenThrow(IllegalArgumentException::class.java)

        val response = orderController.createOrder(productStockRequestDto)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun `test getOrderById with exception`() {
        `when`(orderService.getOrderById(1)).thenThrow(NoSuchElementException::class.java)

        val response: ResponseEntity<OrderDTO> = orderController.getOrderById(1)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun `test updateOrder with exception`() {
        val orderDTO = OrderDTO(1, STATUS.PENDING, listOf(OrderProductDTO(1, 10)))

        `when`(orderService.updateOrder(orderDTO)).thenThrow(NoSuchElementException::class.java)

        val response: ResponseEntity<OrderDTO> = orderController.updateOrder(1, orderDTO)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun `test deleteOrder with exception`() {
        doThrow(NoSuchElementException::class.java).`when`(orderService).deleteOrder(1)

        val response: ResponseEntity<Void> = orderController.deleteOrder(1)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun `test getOrderStatus with exception`() {
        `when`(orderService.getOrderStatus(1)).thenThrow(NoSuchElementException::class.java)

        val response: ResponseEntity<String> = orderController.getOrderStatus(1)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

}