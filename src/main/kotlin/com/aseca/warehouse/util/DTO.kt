package com.aseca.warehouse.util

import com.aseca.warehouse.model.STATUS

data class OrderDTO(
    val id : Long,
    val status: STATUS,
    val orderProducts: List<OrderProductDTO>,
    val warehouseId: Long,
    val shopId: Long
)

data class OrderProductDTO(
    val productId: Long,
    val quantity: Int
)

data class ProductDTO(
    val id: Long,
    val name: String,
    val stockQuantity: Int,
    val warehouseId: Long,
    val shopId: Long
)

data class StockDTO(
    val productId: Long,
    val quantity: Int,
    val shopId: Long
)

data class WarehouseDTO(
    val id: Long,
    val name: String,
    val addressId: Long,
    val stockItems: List<StockDTO>
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
    val shopId: Long,
)

data class ProductStock(
    val productId: Long,
    val quantity: Int,
)