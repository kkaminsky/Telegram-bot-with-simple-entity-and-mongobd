package com.example.lab06.dao

import com.example.lab06.entity.Service
import com.example.lab06.entity.TypeOfAdvertising
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class TypeOfAdvertisingDao @Autowired constructor(
        private val mongoOperations: MongoOperations
) {
    fun save(typeOfAdvertising: TypeOfAdvertising) {
        mongoOperations.save(typeOfAdvertising)
    }

    fun get(id:String): TypeOfAdvertising? {
        return mongoOperations.findOne(Query.query(Criteria.where("id").`is`(id)),TypeOfAdvertising::class.java)
    }

    fun getAll(): List<TypeOfAdvertising> {
        return mongoOperations.findAll(TypeOfAdvertising::class.java)
    }

    fun remove(id: String?) {
        mongoOperations.remove(Query.query(Criteria.where("id").`is`(id!!)), TypeOfAdvertising::class.java)
    }

}