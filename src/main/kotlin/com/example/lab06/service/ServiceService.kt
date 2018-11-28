package com.example.lab06.service

import com.example.lab06.entity.Customer
import com.example.lab06.entity.Service

interface ServiceService {

    fun getAll(): List<Service>

    fun add(serice: Service)

    fun update(service: Service)

    fun get(id: String?): Service?

    fun remove(id: String?)
}