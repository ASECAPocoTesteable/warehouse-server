package model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "addresses")
data class Address(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var street1: String = "",

    @Column(nullable = true)
    var street2: String? = null,

    @Column(nullable = false)
    var city: String = "",

    @Column(nullable = false)
    var zipcode: String = "",

    @Column(nullable = false)
    var number: Int = 0
)
