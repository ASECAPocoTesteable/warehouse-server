package com.aseca.warehouse.repository

import com.aseca.warehouse.model.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StockRepository : JpaRepository<Stock, Long> {

    @Query("SELECT s FROM Stock s WHERE s.product.idProduct = :productId")
    fun findByProductId(productId: Long): List<Stock>

    @Query("SELECT s.quantity FROM Stock s WHERE s.product.idProduct = :productId")
    fun findQuantityByProductId(productId: Long): Int

    @Query("SELECT s FROM Stock s WHERE s.product.idProduct = :productId")
    fun findStockByProductId(productId: Long): Stock?

}
