package com.aseca.warehouse.model

import jakarta.persistence.*

@Entity
@Table(name = "orders")
data class Order(
    @Column(nullable = false)
    var status: STATUS,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderProducts: List<OrderProduct> = mutableListOf(),

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
