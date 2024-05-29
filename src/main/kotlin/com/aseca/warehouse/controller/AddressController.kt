package com.aseca.warehouse.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.aseca.warehouse.service.AddressService
import com.aseca.warehouse.util.AddressDTO
import java.util.*

@RestController
class AddressController(private val addressService: AddressService) {

    @GetMapping("/address/{id}")
    fun getAddress(@PathVariable id: Long): ResponseEntity<AddressDTO> {
        return try {
            ResponseEntity.ok(addressService.getAddressById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/address")
    fun getAddresses(): ResponseEntity<List<AddressDTO>> {
        return try {
            ResponseEntity.ok(addressService.getAddresses())
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/address")
    fun createAddress(@RequestBody addressDTO: AddressDTO): ResponseEntity<AddressDTO> {
        return try {
            ResponseEntity.ok(addressService.createAddress(addressDTO))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/address/{id}")
    fun updateAddress(@PathVariable id: Long, @RequestBody addressDTO: AddressDTO): ResponseEntity<AddressDTO> {
        return try {
            if (id != addressDTO.id) {
                return ResponseEntity.badRequest().build()
            }
            ResponseEntity.ok(addressService.updateAddress(addressDTO))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/address/{id}")
    fun deleteAddress(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            addressService.deleteAddress(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
}