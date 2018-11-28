package com.example.lab06.service

import com.example.lab06.dao.ContactDao
import com.example.lab06.dao.CustomerDao
import com.example.lab06.entity.Contact
import com.example.lab06.entity.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl @Autowired constructor(
        private val customerDao: CustomerDao
):CustomerService {


    override fun getAll(): List<Customer>{
        return customerDao!!.getAll()
    }

    override fun add(customer: Customer) {
        customerDao!!.save(customer)
    }

    override fun update(customer: Customer) {
        customerDao!!.save(customer)
    }

    override fun get(id: String?): Customer? {
        return customerDao!!.get(id!!)
    }

    override fun remove(id: String?) {
        customerDao!!.remove(id)
    }
}