package util

import java.util.*

object productDTO {
    data class ProductDTO(
        val name: String,
        val price: Double,
        val stock: Int,
        val category: String
    )
}
object categoryDTO {
    data class CategoryDTO(
        val name: String
    )
}
object stockDTO {
    data class StockDTO(
        val quantity: Int,
        val productID: UUID
    )
}