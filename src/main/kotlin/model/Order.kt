package model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val status: STATUS,

    @OneToMany(mappedBy = "order")
    val orderProducts: List<OrderProduct> = mutableListOf()
)
