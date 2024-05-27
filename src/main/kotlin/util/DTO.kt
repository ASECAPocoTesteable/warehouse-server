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
    val productId: UUID,
    val quantity: Int
)

data class WarehouseDTO(
    val id: UUID,
    val name: String,
    val addressId: UUID,
    val stockItems: List<StockDTO>
)

data class AddressDTO(
    val id: UUID,
    val street1: String,
    val street2: String?,
    val city: String,
    val zipcode: String,
    val number: Int
)