package repository

import model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    fun findByCategoryId(categoryId: Long): List<Product>

    fun findByName(name: String): Product?

    fun deleteById(id: UUID)

    fun findById(id: UUID): Product?
}
