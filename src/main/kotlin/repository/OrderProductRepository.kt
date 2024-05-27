package repository

import model.OrderProduct
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OrderProductRepository : JpaRepository<OrderProduct, UUID> {

    fun findByOrderId(orderId: UUID): List<OrderProduct>

    fun findByProductId(productId: UUID): List<OrderProduct>
}
