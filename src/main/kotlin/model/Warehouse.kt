package model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "warehouses")
data class Warehouse(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    val address: Address,

    @OneToMany(mappedBy = "warehouse", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val stockItems: List<Stock> = mutableListOf()
)
