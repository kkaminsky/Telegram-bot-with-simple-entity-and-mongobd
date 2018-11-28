package com.example.lab06.dao

import com.example.lab06.entity.Order
import com.example.lab06.entity.Position
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class PositionDao @Autowired constructor(
        private val mongoOperations: MongoOperations
) {
    fun save(position: Position) {
        mongoOperations.save(position)
    }

    fun get(id:String): Position? {
        return mongoOperations.findOne(Query.query(Criteria.where("id").`is`(id)), Position::class.java)
    }

    fun getAll(): List<Position> {
        return mongoOperations.findAll(Position::class.java)
    }

    fun remove(id: String?) {
        mongoOperations.remove(Query.query(Criteria.where("id").`is`(id!!)), Position::class.java)
    }

}