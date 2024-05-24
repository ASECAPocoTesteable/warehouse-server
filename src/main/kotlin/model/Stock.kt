package model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "stock_items")
data class Stock(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val quantity: Int,

    @OneToMany(mappedBy = "stock")
    val shopWarehouseStocks: List<ShopWarehouseStock> = mutableListOf()
)
