package model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "order_product")
data class OrderProduct(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    val product: Product,

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    val order: Order,

    @Column(nullable = false)
    var quantity: Int
)
