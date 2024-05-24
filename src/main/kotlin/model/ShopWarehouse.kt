package model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "shop_warehouse")
data class ShopWarehouse(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    val shop: Shop,

    @ManyToOne
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    val warehouse: Warehouse,

    @OneToMany(mappedBy = "shopWarehouse")
    val shopWarehouseStocks: List<ShopWarehouseStock> = mutableListOf()
)
