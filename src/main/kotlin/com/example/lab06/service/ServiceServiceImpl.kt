package com.example.lab06.service

import com.example.lab06.dao.OrderDao
import com.example.lab06.dao.ServiceDao
import com.example.lab06.entity.Order
import com.example.lab06.entity.Service
import org.springframework.beans.factory.annotation.Autowired

@org.springframework.stereotype.Service
class ServiceServiceImpl @Autowired constructor(
        private val serviceDao: ServiceDao
):ServiceService {

    override fun getAll(): List<Service>{
        return serviceDao!!.getAll()
    }

    override fun add(service: Service) {
        serviceDao!!.save(service)
    }

    override fun update(service: Service) {
        serviceDao!!.save(service)
    }

    override fun get(id: String?): Service? {
        return serviceDao!!.get(id!!)
    }

    override fun remove(id: String?) {
        serviceDao!!.remove(id)
    }
}