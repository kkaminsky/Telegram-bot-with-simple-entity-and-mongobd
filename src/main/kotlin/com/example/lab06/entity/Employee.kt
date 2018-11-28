package com.example.lab06.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "employees")
class Employee {

    @Id
    var id: String? = null

    var name: String? = null

    var age: Int?=null

    var gender: String?=null

    var contact:Contact?= null

    var passport:String?=null

    var position:Position?=null

    override fun toString(): String {
        return "Employee(id=$id, name=$name, age=$age, gender=$gender, contact=$contact, passport=$passport, position=$position)"
    }

}