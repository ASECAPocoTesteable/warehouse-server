package model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "shop_warehouse_stock")
data class ShopWarehouseStock(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "shop_warehouse_id", referencedColumnName = "id")
    val shopWarehouse: ShopWarehouse,

    @ManyToOne
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    val stock: Stock
)
