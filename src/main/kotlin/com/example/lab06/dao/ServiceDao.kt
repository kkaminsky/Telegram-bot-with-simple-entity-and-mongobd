package com.example.lab06.dao

import com.example.lab06.entity.Position
import com.example.lab06.entity.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class ServiceDao  @Autowired constructor(
        private val mongoOperations: MongoOperations
) {
    fun save(service: Service) {
        mongoOperations.save(service)
    }

    fun get(id:String): Service? {
        return mongoOperations.findOne(Query.query(Criteria.where("id").`is`(id)), Service::class.java)
    }

    fun getAll(): List<Service> {
        return mongoOperations.findAll(Service::class.java)
    }

    fun remove(id: String?) {
        mongoOperations.remove(Query.query(Criteria.where("id").`is`(id!!)), Service::class.java)
    }

}