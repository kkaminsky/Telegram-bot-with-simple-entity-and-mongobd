package com.example.lab06.dao

import com.example.lab06.entity.Employee
import com.example.lab06.entity.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class OrderDao @Autowired constructor(
        private val mongoOperations: MongoOperations
) {
    fun save(order: Order) {
        mongoOperations.save(order)
    }

    fun get(id:String): Order? {
        return mongoOperations.findOne(Query.query(Criteria.where("id").`is`(id)), Order::class.java)
    }

    fun getAll(): List<Order> {
        return mongoOperations.findAll(Order::class.java)
    }

    fun remove(id: String?) {
        mongoOperations.remove(Query.query(Criteria.where("id").`is`(id!!)), Order::class.java)
    }

}