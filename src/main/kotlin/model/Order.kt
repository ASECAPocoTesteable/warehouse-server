package model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var status: STATUS,

    @OneToMany(mappedBy = "order")
    var orderProducts: List<OrderProduct> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "warehouse", referencedColumnName = "id")
    val warehouse: Warehouse
)
