package model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "products")
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String,

    @Column(nullable = true)
    val description: String?,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    val stock: Stock? = null,

    @OneToMany(mappedBy = "product")
    val orderProducts: List<OrderProduct> = mutableListOf(),

    @OneToMany(mappedBy = "product")
    val categoryProducts: List<CategoryProduct> = mutableListOf()
)
