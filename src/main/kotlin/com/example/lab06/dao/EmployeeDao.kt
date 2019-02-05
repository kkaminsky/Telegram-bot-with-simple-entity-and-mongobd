package com.example.lab06.dao

import com.example.lab06.entity.Contact
import com.example.lab06.entity.Employee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class EmployeeDao @Autowired constructor(
        private val mongoOperations: MongoOperations
) {
    fun save(employee: Employee) {
        mongoOperations.save(employee)



    }

    fun get(id:String): Employee? {
        return mongoOperations.findOne(Query.query(Criteria.where("id").`is`(id)), Employee::class.java)
    }

    fun getAll(): List<Employee> {
        return mongoOperations.findAll(Employee::class.java)
    }

    fun remove(id: String?) {
        mongoOperations.remove(Query.query(Criteria.where("id").`is`(id!!)), Employee::class.java)
    }

}