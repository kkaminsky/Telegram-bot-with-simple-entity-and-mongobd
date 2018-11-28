package com.example.lab06.service

import com.example.lab06.entity.Contact
import com.example.lab06.entity.Order

interface OrderService {
    fun getAll(): List<Order>

    fun add(order: Order)

    fun update(order: Order)

    fun get(id: String?): Order?

    fun remove(id: String?)
}