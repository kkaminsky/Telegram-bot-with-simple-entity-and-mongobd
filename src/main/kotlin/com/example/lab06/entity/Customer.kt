package com.example.lab06.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "customers")
class Customer {
    @Id
    var id: String? = null

    var name:String?=null

    var contact:Contact?=null

    override fun toString(): String {
        return "Customer(id=$id, name=$name, contact=$contact)"
    }

}