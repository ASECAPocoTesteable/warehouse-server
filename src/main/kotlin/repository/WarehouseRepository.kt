package repository

import model.Warehouse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WarehouseRepository : JpaRepository<Warehouse, Long> {

}