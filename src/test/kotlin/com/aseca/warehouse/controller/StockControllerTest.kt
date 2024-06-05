package com.aseca.warehouse.controller

import com.aseca.warehouse.exception.InsufficientStockException
import com.aseca.warehouse.model.Product
import com.aseca.warehouse.model.Stock
import com.aseca.warehouse.service.StockService
import com.aseca.warehouse.util.ProductStock
import com.aseca.warehouse.util.StockDTO
import com.aseca.warehouse.util.ProductStockRequestDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.ResponseEntity
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockitoExtension::class)
class StockControllerTest {

    @Mock
    private lateinit var stockService: StockService

    @InjectMocks
    private lateinit var stockController: StockController

    @Test
    fun `test updateStock`() {
        val stockDTO = StockDTO(1, 10)
        val stock = Stock(10, Product("Test Product",idProduct= 1))
        stock.id = 1

        `when`(stockService.updateStock(1, stockDTO)).thenReturn(stock)

        val updatedStock = stockController.updateStock(1, stockDTO)

        assertEquals(ResponseEntity.ok(stockDTO), updatedStock)
        verify(stockService).updateStock(1, stockDTO)
    }

    @Test
    fun `test getStockById`() {
        val stock = Stock(10, Product("Test Product", idProduct=1))
        stock.id = 1

        `when`(stockService.getStockById(1)).thenReturn(stock)

        val foundStock = stockController.getStockById(1)

        assertEquals(ResponseEntity.ok(StockDTO(1, 10)), foundStock)
        verify(stockService).getStockById(1)
    }

    @Test
    fun `test checkStock`() {
        val productStockRequestDto = ProductStockRequestDto(listOf(ProductStock(1, 10)))

        `when`(stockService.checkStock(productStockRequestDto)).thenReturn(true)

        val isStockAvailable = stockController.checkStock(productStockRequestDto)

        assertEquals(ResponseEntity.ok(true), isStockAvailable)
        verify(stockService).checkStock(productStockRequestDto)
    }

    @Test
    fun `test updateStock with exception`() {
        val stockDTO = StockDTO(1, 10)

        `when`(stockService.updateStock(1, stockDTO)).thenThrow(NoSuchElementException::class.java)

        val response = stockController.updateStock(1, stockDTO)

        assertEquals(ResponseEntity.notFound().build<Stock>(), response)
    }

    @Test
    fun `test getStockById with exception`() {
        `when`(stockService.getStockById(1)).thenThrow(NoSuchElementException::class.java)

        val response = stockController.getStockById(1)

        assertEquals(ResponseEntity.notFound().build<Stock>(), response)
    }

    @Test
    fun `test checkStock with exception`() {
        val productStockRequestDto = ProductStockRequestDto(listOf(ProductStock(1, 10)))

        `when`(stockService.checkStock(productStockRequestDto)).thenThrow(InsufficientStockException::class.java)

        assertThrows<InsufficientStockException> {
            stockController.checkStock(productStockRequestDto)
        }
    }
}
