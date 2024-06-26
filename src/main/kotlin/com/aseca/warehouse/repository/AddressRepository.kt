package com.aseca.warehouse.repository

import com.aseca.warehouse.model.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : JpaRepository<Address, Long>{
}