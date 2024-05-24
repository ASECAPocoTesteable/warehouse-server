package model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "returns")
data class Return(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    val order: Order,

    @Column(nullable = false)
    val reason: String,

    @Column(nullable = false)
    val status: STATUS
)
