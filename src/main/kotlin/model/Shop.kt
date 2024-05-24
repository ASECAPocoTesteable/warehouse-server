package model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "shops")
data class Shop(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = true)
    val description: String? = null,

    @OneToMany(mappedBy = "shop")
    val shopWarehouses: List<ShopWarehouse> = mutableListOf()
)
