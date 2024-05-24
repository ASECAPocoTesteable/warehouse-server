package model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "addresses")
class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID()

    @Column(nullable = false)
    val street1 : String = ""

    @Column(nullable = true)
    val street2 : String? = null

    @Column(nullable = false)
    val city : String = ""

    @Column(nullable = false)
    val zipcode : String = ""

    @Column(nullable = false)
    val number : Int = 0

}