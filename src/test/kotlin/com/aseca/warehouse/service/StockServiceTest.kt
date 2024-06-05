package com.aseca.warehouse.service

import com.aseca.warehouse.model.Product
import com.aseca.warehouse.model.Stock
import com.aseca.warehouse.repository.StockRepository
import com.aseca.warehouse.util.ProductStockRequestDto
import com.aseca.warehouse.util.ProductStock
import com.aseca.warehouse.util.StockDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class StockServiceTest {

    @Mock
    private lateinit var stockRepository: StockRepository

    @InjectMocks
    private lateinit var stockService: StockService

    @Test
    fun `test updateStock`() {
        val product = Product("Test Product", idProduct = 1)
        val stock = Stock(10, product)
        val stockDTO = StockDTO(1, 20)

        `when`(stockRepository.findById(1)).thenReturn(Optional.of(stock))
        `when`(stockRepository.save(stock)).thenReturn(stock)

        val updatedStock = stockService.updateStock(1, stockDTO)

        assertEquals(20, updatedStock.quantity)
        verify(stockRepository).findById(1)
        verify(stockRepository).save(stock)
    }

    @Test
    fun `test getStockById`() {
        val product = Product("Test Product", idProduct = 1)
        val stock = Stock(10, product)

        `when`(stockRepository.findById(1)).thenReturn(Optional.of(stock))

        val foundStock = stockService.getStockById(1)

        assertEquals(10, foundStock.quantity)
        verify(stockRepository).findById(1)
    }

    @Test
    fun `test checkStock`() {
        val stockRequest = ProductStockRequestDto(listOf(ProductStock(1, 5)))
        val product = Product("Test Product", idProduct = 1)
        val stock = Stock(10, product)

        `when`(stockRepository.findStockByProductId(1)).thenReturn(stock)

        val isStockAvailable = stockService.checkStock(stockRequest)

        assertTrue(isStockAvailable)
        verify(stockRepository).findStockByProductId(1)
    }

    @Test
    fun `test reduceProductStock`() {
        val product = Product("Test Product", idProduct = 1)
        val stock = Stock(10, product)

        `when`(stockRepository.findByProductId(product.idProduct)).thenReturn(listOf(stock))
        `when`(stockRepository.save(any(Stock::class.java))).thenReturn(stock) // Add this line

        stockService.reduceProductStock(product.idProduct, 5)

        assertEquals(5, stock.quantity)
        verify(stockRepository).save(stock)
    }

    @Test
    fun `test createStock`() {
        val product = Product("New product", idProduct = 1)
        val stock = Stock(10, product)

        `when`(stockRepository.findByProductId(product.idProduct)).thenReturn(emptyList())
        `when`(stockRepository.save(any(Stock::class.java))).thenReturn(stock)

        val createdStock = stockService.createStock(stock)

        assertEquals(stock, createdStock)
        verify(stockRepository).save(stock)
    }

    @Test
    fun `test getStockByProductId`() {
        val product = Product("Test Product", idProduct = 1)
        val stock = Stock(10, product)

        `when`(stockRepository.findQuantityByProductId(product.idProduct)).thenReturn(10)

        val foundStock = stockService.getStockByProductId(product.idProduct)

        assertEquals(10, foundStock)
        verify(stockRepository).findQuantityByProductId(product.idProduct)
    }

    @Test
    fun `test getStockByProductId with non-existing id`() {
        `when`(stockRepository.findQuantityByProductId(anyLong())).thenReturn(0)

        val foundStock = stockService.getStockByProductId(1)

        assertEquals(0, foundStock)
        verify(stockRepository).findQuantityByProductId(1)
    }

}