package model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "stock_items")
data class Stock(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var quantity: Int,

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    val product: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "id")
    val warehouse: Warehouse,

    @Column(nullable = false)
    val shopId: UUID
)
