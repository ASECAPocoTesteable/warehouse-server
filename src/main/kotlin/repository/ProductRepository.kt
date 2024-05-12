package repository

import model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.util.*

@EnableJpaRepositories
interface ProductRepository : JpaRepository<Product, UUID> {

    fun findByName(name: String): Product?

}