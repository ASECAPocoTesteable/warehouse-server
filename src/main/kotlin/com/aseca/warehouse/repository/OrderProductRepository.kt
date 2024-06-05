package com.aseca.warehouse.repository

import com.aseca.warehouse.model.OrderProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderProductRepository : JpaRepository<OrderProduct, Long>
