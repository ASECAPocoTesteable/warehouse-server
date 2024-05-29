package com.aseca.warehouse.service

import com.aseca.warehouse.model.Warehouse
import org.springframework.stereotype.Service
import com.aseca.warehouse.repository.AddressRepository
import com.aseca.warehouse.repository.WarehouseRepository
import com.aseca.warehouse.util.WarehouseDTO
import java.util.*
import kotlin.NoSuchElementException

@Service
class WarehouseService(private val warehouseRepository: WarehouseRepository, private val addressRepository: AddressRepository) {

    fun getWarehouseById(id: Long): Warehouse {
        return warehouseRepository.findById(id).get()
    }

    fun getWarehouse(id: Long): Warehouse {
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

    fun deleteWarehouse(id: Long) = warehouseRepository.deleteById(id)

    private fun checkWarehouseExists(id: Long): Warehouse {
        return warehouseRepository.findById(id).orElseThrow { NoSuchElementException("Warehouse not found") }
    }
}