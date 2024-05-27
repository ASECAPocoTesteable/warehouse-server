package controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import service.AddressService
import util.AddressDTO
import java.util.*

@RestController
@RequestMapping("/address")
class AddressController(private val addressService: AddressService) {

    @GetMapping("/{id}")
    fun getAddress(@PathVariable id: UUID): ResponseEntity<AddressDTO> {
        return try {
            ResponseEntity.ok(addressService.getAddressById(id))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAddresses(): ResponseEntity<List<AddressDTO>> {
        return try {
            ResponseEntity.ok(addressService.getAddresses())
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping
    fun createAddress(@RequestBody addressDTO: AddressDTO): ResponseEntity<AddressDTO> {
        return try {
            ResponseEntity.ok(addressService.createAddress(addressDTO))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/{id}")
    fun updateAddress(@PathVariable id: UUID, @RequestBody addressDTO: AddressDTO): ResponseEntity<AddressDTO> {
        return try {
            if (id != addressDTO.id) {
                return ResponseEntity.badRequest().build()
            }
            ResponseEntity.ok(addressService.updateAddress(addressDTO))
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteAddress(@PathVariable id: UUID): ResponseEntity<Void> {
        return try {
            addressService.deleteAddress(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
}