package com.aseca.warehouse.repository

import com.aseca.warehouse.model.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StockRepository : JpaRepository<Stock, Long> {

    @Query("SELECT s FROM Stock s WHERE s.product.id = :productId")
    fun findByProductId(productId: Long): List<Stock>

    @Query("SELECT s.quantity FROM Stock s WHERE s.product.id = :productId AND s.warehouse.id = :warehouseId")
    fun findByProductIdAndWarehouseId(productId: Long, warehouseId: Long): Int
}
