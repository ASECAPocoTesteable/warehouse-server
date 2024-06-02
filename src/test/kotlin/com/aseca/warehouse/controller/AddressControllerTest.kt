package com.aseca.warehouse.controller

import com.aseca.warehouse.controller.AddressController
import com.aseca.warehouse.service.AddressService
import com.aseca.warehouse.util.AddressDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.ResponseEntity
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.NoSuchElementException

@ExtendWith(MockitoExtension::class)
class AddressControllerTest {

    @Mock
    private lateinit var addressService: AddressService

    @InjectMocks
    private lateinit var addressController: AddressController

    @Test
    fun `test getAddress`() {
        val addressDTO = AddressDTO(1, "Street1", "Street2", "City", "Zipcode", 123)

        `when`(addressService.getAddressById(1)).thenReturn(addressDTO)

        val response = addressController.getAddress(1)

        assertEquals(ResponseEntity.ok(addressDTO), response)
    }

    @Test
    fun `test getAddress with exception`() {
        `when`(addressService.getAddressById(1)).thenThrow(NoSuchElementException::class.java)

        val response = addressController.getAddress(1)

        assertEquals(ResponseEntity.notFound().build<AddressDTO>(), response)
    }

    @Test
    fun `test createAddress`() {
        val addressDTO = AddressDTO(1, "Street1", "Street2", "City", "Zipcode", 123)

        `when`(addressService.createAddress(addressDTO)).thenReturn(addressDTO)

        val response = addressController.createAddress(addressDTO)

        assertEquals(ResponseEntity.ok(addressDTO), response)
    }

    @Test
    fun `test updateAddress`() {
        val addressDTO = AddressDTO(1, "Street1", "Street2", "City", "Zipcode", 123)

        `when`(addressService.updateAddress(addressDTO)).thenReturn(addressDTO)

        val response = addressController.updateAddress(1, addressDTO)

        assertEquals(ResponseEntity.ok(addressDTO), response)
    }

    @Test
    fun `test deleteAddress`() {
        `when`(addressService.deleteAddress(1)).then { }

        val response = addressController.deleteAddress(1)

        assertEquals(ResponseEntity.noContent().build<Void>(), response)
    }

    @Test
    fun `test getAllAddresses`() {
        val addresses = listOf(AddressDTO(1, "Street1", "Street2", "City", "Zipcode", 123))

        `when`(addressService.getAddresses()).thenReturn(addresses)

        val allAddresses = addressController.getAddresses()

        assertEquals(ResponseEntity.ok(addresses), allAddresses)
    }

    @Test
    fun `test createAddress with exception`() {
        val addressDTO = AddressDTO(1, "Street1", "Street2", "City", "Zipcode", 123)

        `when`(addressService.createAddress(addressDTO)).thenThrow(RuntimeException::class.java)

        val response = addressController.createAddress(addressDTO)

        assertEquals(ResponseEntity.badRequest().build<AddressDTO>(), response)
    }

    @Test
    fun `test updateAddress with exception`() {
        val addressDTO = AddressDTO(1, "Street1", "Street2", "City", "Zipcode", 123)

        `when`(addressService.updateAddress(addressDTO)).thenThrow(NoSuchElementException::class.java)

        val response = addressController.updateAddress(1, addressDTO)

        assertEquals(ResponseEntity.notFound().build<AddressDTO>(), response)
    }

    @Test
    fun `test deleteAddress with exception`() {
        `when`(addressService.deleteAddress(1)).thenThrow(NoSuchElementException::class.java)

        val response = addressController.deleteAddress(1)

        assertEquals(ResponseEntity.notFound().build<Void>(), response)
    }

    @Test
    fun `test getAllAddresses with exception`() {
        `when`(addressService.getAddresses()).thenThrow(RuntimeException::class.java)

        val allAddresses = addressController.getAddresses()

        assertEquals(ResponseEntity.badRequest().build<List<AddressDTO>>(), allAddresses)
    }
}