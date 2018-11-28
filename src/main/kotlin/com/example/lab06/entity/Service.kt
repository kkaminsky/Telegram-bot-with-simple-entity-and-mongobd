package com.example.lab06.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "services")
class Service {

    @Id
    var id:String?=null

    var name:String?= null

    var description:String?=null

    var cost:Double?=null

    override fun toString(): String {
        return "Service(id=$id, name=$name, description=$description, cost=$cost)"
    }
}