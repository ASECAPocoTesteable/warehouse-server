package com.aseca.warehouse.repository

import com.aseca.warehouse.model.OrderProduct
import org.springframework.data.jpa.repository.JpaRepository

interface OrderProductRepository : JpaRepository<OrderProduct, Long>
