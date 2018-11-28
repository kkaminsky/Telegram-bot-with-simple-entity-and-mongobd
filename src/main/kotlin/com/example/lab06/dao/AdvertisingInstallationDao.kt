package com.example.lab06.dao

import com.example.lab06.entity.AdvertisingInstallation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class AdvertisingInstallationDao @Autowired constructor(
        private val mongoOperations: MongoOperations
){
    fun save(advertisingInstallation: AdvertisingInstallation) {
        mongoOperations.save(advertisingInstallation)
    }

    fun get(id:String): AdvertisingInstallation? {
        return mongoOperations.findOne(Query.query(Criteria.where("id").`is`(id)), AdvertisingInstallation::class.java)
    }

    fun getAll(): List<AdvertisingInstallation> {
        return mongoOperations.findAll(AdvertisingInstallation::class.java)
    }

    fun remove(id: String?) {
        mongoOperations.remove(Query.query(Criteria.where("id").`is`(id!!)), AdvertisingInstallation::class.java)
    }
}