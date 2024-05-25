package model

import jakarta.annotation.Nullable
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "categories")
data class Category(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = true)
    val description: String? = null,

    @OneToMany(mappedBy = "category")
    @Nullable
    val categoryProducts: List<CategoryProduct> = mutableListOf()
)
