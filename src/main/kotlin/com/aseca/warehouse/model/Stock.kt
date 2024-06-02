package com.aseca.warehouse.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "stock_items")
data class Stock(

    @Column(nullable = false)
    var quantity: Int,

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    val product: Product,

)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
