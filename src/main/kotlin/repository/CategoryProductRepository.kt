package repository

import model.CategoryProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CategoryProductRepository : JpaRepository<CategoryProduct, UUID> {

    fun findByName(name: String): CategoryProduct?

    @Query("SELECT cp.category FROM CategoryProduct cp WHERE cp.product.id = :productId")
    fun findProductCategories(productId: UUID): List<CategoryProduct>
}