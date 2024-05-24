package model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "category_product")
data class CategoryProduct(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    val product: Product,

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    val category: Category
)
