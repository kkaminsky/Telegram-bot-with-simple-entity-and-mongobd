package com.example.lab06.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable


@Document(collection = "contacts")
class Contact : Serializable {

    @Id
    var id: String? = null

    var number: String? = null

    var email: String? = null

    override fun toString(): String {
        return "Contact(id=$id, number=$number, email=$email)"
    }


}