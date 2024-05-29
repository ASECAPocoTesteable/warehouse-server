package com.aseca.warehouse.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "returns")
data class Return(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    val order: Order,

    @Column(nullable = false)
    val reason: String,

    @Column(nullable = false)
    val status: STATUS
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
