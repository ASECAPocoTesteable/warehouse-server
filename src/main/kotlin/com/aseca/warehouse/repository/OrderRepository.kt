package com.aseca.warehouse.repository

import com.aseca.warehouse.model.Order
import com.aseca.warehouse.model.STATUS
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.status = :status")
    fun findByStatus(status: STATUS): List<Order>
}