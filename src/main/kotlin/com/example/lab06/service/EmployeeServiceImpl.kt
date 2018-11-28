package com.example.lab06.service


import com.example.lab06.dao.EmployeeDao
import com.example.lab06.entity.Employee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeServiceImpl @Autowired constructor(
        private val employeeDao: EmployeeDao
):EmployeeService {
    override fun getAll(): List<Employee>{
        return employeeDao!!.getAll()
    }

    override fun add(order: Employee) {
        employeeDao!!.save(order)
    }

    override fun update(order: Employee) {
        employeeDao!!.save(order)
    }

    override fun get(id: String?):Employee? {
        return employeeDao!!.get(id!!)
    }

    override fun remove(id: String?) {
        employeeDao!!.remove(id)
    }
}