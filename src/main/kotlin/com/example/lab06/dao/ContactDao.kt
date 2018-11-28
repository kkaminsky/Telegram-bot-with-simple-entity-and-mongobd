package com.example.lab06.dao

import com.example.lab06.entity.Contact
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.core.query.Query.query
import com.sun.deploy.util.SearchPath.findOne



@Repository
class ContactDao @Autowired constructor(
       private val mongoOperations: MongoOperations
) {
    fun save(contact: Contact) {
        mongoOperations.save(contact)
    }

    fun get(id:String):Contact? {
        return mongoOperations.findOne(Query.query(Criteria.where("id").`is`(id)), Contact::class.java)
    }

    fun getAll(): List<Contact> {
        return mongoOperations.findAll(Contact::class.java)
    }

    fun remove(id: String?) {
        mongoOperations.remove(Query.query(Criteria.where("id").`is`(id!!)), Contact::class.java)
    }

}