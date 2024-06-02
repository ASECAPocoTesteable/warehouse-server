package com.aseca.warehouse.service

import com.aseca.warehouse.model.Address
import org.springframework.stereotype.Service
import com.aseca.warehouse.repository.AddressRepository
import com.aseca.warehouse.util.AddressDTO
import java.util.*
import kotlin.NoSuchElementException

@Service
class AddressService(private val addressRepository: AddressRepository) {

    fun getAddressById(id: Long): AddressDTO {
        val address = addressRepository.findById(id).orElseThrow { NoSuchElementException("Address not found") }
        return AddressDTO(address.id, address.street1, address.street2, address.city, address.zipcode, address.number)
    }

    fun getAddresses(): List<AddressDTO> {
        return addressRepository.findAll().map { address ->
            AddressDTO(address.id, address.street1, address.street2, address.city, address.zipcode, address.number)
        }
    }

    fun createAddress(addressDTO: AddressDTO): AddressDTO {
        val address = Address(street1 = addressDTO.street1, street2 = addressDTO.street2, city = addressDTO.city, zipcode = addressDTO.zipcode, number = addressDTO.number)
        val savedAddress = addressRepository.save(address)
        return AddressDTO(savedAddress.id, savedAddress.street1, savedAddress.street2, savedAddress.city, savedAddress.zipcode, savedAddress.number)
    }

    fun updateAddress(addressDTO: AddressDTO): AddressDTO {
        val address = addressRepository.findById(addressDTO.id).orElseThrow { NoSuchElementException("Address not found") }
        address.street1 = addressDTO.street1
        address.street2 = addressDTO.street2
        address.city = addressDTO.city
        address.zipcode = addressDTO.zipcode
        address.number = addressDTO.number
        val updatedAddress = addressRepository.save(address)
        return AddressDTO(updatedAddress.id, updatedAddress.street1, updatedAddress.street2, updatedAddress.city, updatedAddress.zipcode, updatedAddress.number)
    }

    fun deleteAddress(id: Long) = addressRepository.deleteById(id)

    fun checkAddressExists(id: Long): AddressDTO {
        val address = addressRepository.findById(id).orElseThrow { NoSuchElementException("Address not found") }
        return AddressDTO(address.id, address.street1, address.street2, address.city, address.zipcode, address.number)
    }
}