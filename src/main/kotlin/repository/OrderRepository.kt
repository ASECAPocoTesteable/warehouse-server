package repository

import model.Order
import model.STATUS
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository : JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o WHERE o.status = :status")
    fun findByStatus(status: STATUS): List<Order>
}