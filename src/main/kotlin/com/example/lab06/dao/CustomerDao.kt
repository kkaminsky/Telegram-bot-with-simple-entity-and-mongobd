package com.example.lab06.dao

import com.example.lab06.entity.Contact
import com.example.lab06.entity.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class CustomerDao @Autowired constructor(
        private val mongoOperations: MongoOperations
) {
    fun save(customer: Customer) {
        mongoOperations.save(customer)
    }

    fun get(id:String): Customer? {
        return mongoOperations.findOne(Query.query(Criteria.where("id").`is`(id)), Customer::class.java)
    }

    fun getAll(): List<Customer> {
        return mongoOperations.findAll(Customer::class.java)
    }

    fun remove(id: String?) {
        mongoOperations.remove(Query.query(Criteria.where("id").`is`(id!!)), Customer::class.java)
    }

}