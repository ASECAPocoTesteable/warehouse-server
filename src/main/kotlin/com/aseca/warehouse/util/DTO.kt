package com.aseca.warehouse.util

import com.aseca.warehouse.model.STATUS

data class OrderDTO(
    val id : Long,
    val status: STATUS,
    val orderProducts: List<OrderProductDTO>
)

data class OrderProductDTO(
    val productId: Long,
    val quantity: Int
)

data class ProductDTO(
    val id: Long,
    val name: String,
    val stockQuantity: Int
)

data class UpdateProductDTO(
    val id: Long,
    val addedQuantity: Int
)

data class StockDTO(
    val productId: Long,
    val quantity: Int
)

data class AddressDTO(
    val id: Long,
    val street1: String,
    val street2: String?,
    val city: String,
    val zipcode: String,
    val number: Int
)
data class ProductStockRequestDto(
    val productList: List<ProductStock>,
)

data class ProductStock(
    val productId: Long,
    val quantity: Int,
)