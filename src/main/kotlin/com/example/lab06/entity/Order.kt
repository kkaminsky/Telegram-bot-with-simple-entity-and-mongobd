package com.example.lab06.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document(collection = "orders")
class Order {
    @Id
    var id: String? = null

    var orderDate:LocalDate?=null

    var startDate:LocalDate?=null

    var finishDate:LocalDate?=null

    var customer:Customer? =null

    var location:String?=null

    var services:List<Service>?=null

    var cost:Double?=null

    var bit:Boolean?=null

    var employee:Employee?=null
    override fun toString(): String {
        return "Order(id=$id, orderDate=$orderDate, startDate=$startDate, finishDate=$finishDate, customer=$customer, location=$location, services=$services, cost=$cost, bit=$bit, employee=$employee)"
    }

}