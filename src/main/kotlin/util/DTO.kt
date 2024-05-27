package util

import model.STATUS
import java.util.*

data class OrderDTO(
    val id : UUID,
    val status: STATUS,
    val orderProducts: List<OrderProductDTO>,
    val warehouseId: UUID
)

data class OrderProductDTO(
    val productId: UUID,
    val quantity: Int
)

data class ProductDTO(
    val id: UUID,
    val name: String,
    val stockQuantity: Int,
    val warehouseId: UUID
)

data class StockDTO(
    val quantity: Int
)