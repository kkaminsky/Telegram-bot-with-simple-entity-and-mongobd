package com.example.lab06.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "positions")
class Position {

    @Id
    var id: String? = null

    var name:String?=null

    var salary:Double?=null

    var duties:String?=null

    var requirements:String? = null
    override fun toString(): String {
        return "Position(id=$id, name=$name, salary=$salary, duties=$duties, requirements=$requirements)"
    }


}