package repository

import model.Warehouse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WarehouseRepository : JpaRepository<Warehouse, UUID> {

    fun findByName(name: String): Warehouse?

    @Query("SELECT w FROM Warehouse w WHERE w.address.city = :city")
    fun findByCity(city: String): Warehouse?
}
