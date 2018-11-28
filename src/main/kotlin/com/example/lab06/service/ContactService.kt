package com.example.lab06.service

import com.example.lab06.entity.Contact

interface ContactService {
    fun getAll(): List<Contact>

    fun add(contact: Contact)

    fun update(contact: Contact)

    fun get(id: String?): Contact?

    fun remove(id: String?)
}