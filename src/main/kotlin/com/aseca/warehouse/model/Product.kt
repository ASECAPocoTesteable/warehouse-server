package com.aseca.warehouse.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "products")
data class Product(

    @Column(nullable = false)
    var name: String,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    val stock: Stock? = null,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
