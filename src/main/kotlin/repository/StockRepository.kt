package repository

import model.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StockRepository : JpaRepository<Stock, UUID> {

    fun createStock(stock: Stock): Stock

    @Query("SELECT s FROM Stock s WHERE s.product.id = :id")
    fun getStockByProductID(id: UUID): Stock

    @Query("SELECT DISTINCT s FROM Stock s")
    fun getStock(): List<Stock>

    @Query("SELECT s FROM Stock s WHERE s.product.id = :productId")
    fun findByProductId(productId: UUID): List<Stock>

    @Query("SELECT s.quantity FROM Stock s WHERE s.product.id = :productId AND s.warehouse.id = :warehouseId")
    fun findByProductIdAndWarehouseId(productId: UUID, warehouseId: UUID): Int

    @Query("SELECT s FROM Stock s WHERE s.product.id = :productId AND s.shopId = :shopId")
    fun findByProductIdAndShopId(productId: UUID, shopId: UUID): Stock
}
