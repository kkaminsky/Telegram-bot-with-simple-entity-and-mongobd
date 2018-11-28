package com.example.lab06.service

import com.example.lab06.entity.Employee


interface EmployeeService {
    fun getAll(): List<Employee>

    fun add(employee: Employee)

    fun update(employee: Employee)

    fun get(id: String?): Employee?

    fun remove(id: String?)
}