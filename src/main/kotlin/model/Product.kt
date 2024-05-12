package model

import jakarta.persistence.Table
import java.util.UUID

@Table(name = "product")
class Product(
    var id: UUID,
    var name: String
)