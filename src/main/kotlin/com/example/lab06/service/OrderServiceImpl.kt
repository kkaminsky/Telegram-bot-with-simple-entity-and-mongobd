package com.example.lab06.service

import com.example.lab06.dao.ContactDao
import com.example.lab06.dao.OrderDao
import com.example.lab06.entity.Contact
import com.example.lab06.entity.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl @Autowired constructor(
        private val orderDao: OrderDao
):OrderService {

    override fun getAll(): List<Order>{
        return orderDao!!.getAll()
    }

    override fun add(order: Order) {
        orderDao!!.save(order)
    }

    override fun update(order: Order) {
        orderDao!!.save(order)
    }

    override fun get(id: String?):Order? {
        return orderDao!!.get(id!!)
    }

    override fun remove(id: String?) {
        orderDao!!.remove(id)
    }
}