package com.example.lab06.service

import com.example.lab06.entity.Contact
import com.example.lab06.entity.Customer

interface CustomerService {

    fun getAll(): List<Customer>

    fun add(customer: Customer)

    fun update(customer: Customer)

    fun get(id: String?): Customer?

    fun remove(id: String?)
}