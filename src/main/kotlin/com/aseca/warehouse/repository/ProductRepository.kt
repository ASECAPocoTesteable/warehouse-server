package com.aseca.warehouse.repository

import com.aseca.warehouse.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, Long>{

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    fun findByName(name: String): List<Product>

    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    fun findByProductId(productId: Long): Optional<Product>
}