package com.example.lab06.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "advertisingInstallations")
class AdvertisingInstallation {
    @Id
    var id: String? = null

    var name:String?=null

    var location:String?=null

    var typeOfAdvertising:TypeOfAdvertising?=null

    var description:String?=null

    var cost:Double?=null

    override fun toString(): String {
        return "AdvertisingInstallation(id=$id, name=$name, location=$location, typeOfAdvertising=$typeOfAdvertising, description=$description, cost=$cost)"
    }

}