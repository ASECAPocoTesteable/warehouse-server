package repository

import model.Product
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProductRepository : JpaRepository<Product, Long> {

    fun findByCategoryId(categoryId: Long): List<Product>

    fun findByName(name: String): Product?

    fun deleteById(id: UUID)

    fun findById(id: UUID): Product?
}
