package service

import model.Warehouse
import org.springframework.stereotype.Service
import repository.AddressRepository
import repository.WarehouseRepository
import util.WarehouseDTO
import java.util.*
import kotlin.NoSuchElementException

@Service
class WarehouseService(private val warehouseRepository: WarehouseRepository, private val addressRepository: AddressRepository) {

    fun getWarehouseById(id: UUID): Warehouse {
        return warehouseRepository.findById(id).get()
    }

    fun getWarehouse(id: UUID): Warehouse {
        return warehouseRepository.findById(id).get()
    }

    fun getWarehouseByName(name: String): Warehouse {
        return warehouseRepository.findByName(name) ?: throw NoSuchElementException("Warehouse not found")
    }

    fun getWarehouses(): List<Warehouse> {
        return warehouseRepository.findAll()
    }

    fun createWarehouse(warehouseDTO: WarehouseDTO): Warehouse {
        val address = addressRepository.findById(warehouseDTO.addressId).get()
        val warehouse = Warehouse(name = warehouseDTO.name, address = address)
        return warehouseRepository.save(warehouse)
    }

    fun updateWarehouse(warehouseDTO: WarehouseDTO): Warehouse {
        val warehouse = checkWarehouseExists(warehouseDTO.id)
        warehouse.name = warehouseDTO.name
        val address = addressRepository.findById(warehouseDTO.addressId).orElseThrow { NoSuchElementException("Address not found") }
        warehouse.address = address
        return warehouseRepository.save(warehouse)
    }

    fun deleteWarehouse(id: UUID) = warehouseRepository.deleteById(id)

    private fun checkWarehouseExists(id: UUID): Warehouse {
        return warehouseRepository.findById(id).orElseThrow { NoSuchElementException("Warehouse not found") }
    }
}