package com.aseca.warehouse.service

import com.aseca.warehouse.model.Address
import com.aseca.warehouse.repository.AddressRepository
import com.aseca.warehouse.util.AddressDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class AddressServiceTest {

    @Mock
    private lateinit var addressRepository: AddressRepository

    @InjectMocks
    private lateinit var addressService: AddressService

    @Test
    fun `test getAddressById`() {
        val address = Address("Street 1", "Street 2", "City", "Zipcode", 123)
        address.id = 1

        `when`(addressRepository.findById(1)).thenReturn(Optional.of(address))

        val foundAddress = addressService.getAddressById(1)

        assertEquals(1, foundAddress.id)
        verify(addressRepository).findById(1)
    }

    @Test
    fun `test createAddress`() {
        val addressDTO = AddressDTO(1, "Street 1", "Street 2", "City", "Zipcode", 123)
        val address = Address("Street 1", "Street 2", "City", "Zipcode", 123)
        address.id = 1

        `when`(addressRepository.save(any(Address::class.java))).thenReturn(address)

        val createdAddress = addressService.createAddress(addressDTO)

        assertEquals(addressDTO, createdAddress)
        verify(addressRepository).save(any(Address::class.java))
    }

    @Test
    fun `test updateAddress`() {
        val address = Address("Street 1", "Street 2", "City", "Zipcode", 123)
        address.id = 1
        val addressDTO = AddressDTO(1, "Updated Street 1", "Updated Street 2", "Updated City", "Updated Zipcode", 456)

        `when`(addressRepository.findById(1)).thenReturn(Optional.of(address))
        `when`(addressRepository.save(any(Address::class.java))).thenReturn(address)

        val updatedAddress = addressService.updateAddress(addressDTO)

        assertEquals(addressDTO, updatedAddress)
        verify(addressRepository).save(any(Address::class.java))
    }

    @Test
    fun `test deleteAddress`() {
        val address = Address("Street 1", "Street 2", "City", "Zipcode", 123)
        address.id = 1

        addressService.deleteAddress(1)

        verify(addressRepository).deleteById(1)
    }

    @Test
    fun `test checkAddressExists`() {
        val address = Address("Street 1", "Street 2", "City", "Zipcode", 123)
        address.id = 1

        `when`(addressRepository.findById(1)).thenReturn(Optional.of(address))

        val foundAddress = addressService.checkAddressExists(1)

        assertEquals(1, foundAddress.id)
        verify(addressRepository).findById(1)
    }

    @Test
    fun `test getAddresses`() {
        val address1 = Address("Street 1", "Street 2", "City", "Zipcode", 123)
        address1.id = 1
        val address2 = Address("Street 3", "Street 4", "City", "Zipcode", 456)
        address2.id = 2

        `when`(addressRepository.findAll()).thenReturn(listOf(address1, address2))

        val foundAddresses = addressService.getAddresses()

        assertEquals(2, foundAddresses.size)
        verify(addressRepository).findAll()
    }
}