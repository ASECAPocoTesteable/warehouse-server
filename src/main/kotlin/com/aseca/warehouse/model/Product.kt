package com.aseca.warehouse.model

import jakarta.persistence.*

@Entity
@Table(name = "products")
data class Product(

    @Column(nullable = false)
    var name: String,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    var stock: Stock? = null,

    var idProduct: Long
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
