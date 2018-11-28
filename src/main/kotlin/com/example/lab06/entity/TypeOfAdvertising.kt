package com.example.lab06.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "typesOfAdvertising")
class TypeOfAdvertising {

    @Id
    var id: String? = null

    var name: String? = null

    var description:String?=null

    override fun toString(): String {
        return "TypeOfAdvertising(id=$id, name=$name, description=$description)"
    }


}