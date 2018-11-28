package com.example.lab06.service

import com.example.lab06.dao.ContactDao
import com.example.lab06.entity.Contact
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ContactServiceImpl  @Autowired constructor(
        private val contactDao: ContactDao
):ContactService {


    override fun getAll(): List<Contact>{
        return contactDao!!.getAll()
    }

    override fun add(contact: Contact) {
        contactDao!!.save(contact)
    }

    override fun update(contact: Contact) {
        contactDao!!.save(contact)
    }

    override fun get(id: String?): Contact? {
        return contactDao!!.get(id!!)
    }

    override fun remove(id: String?) {
        contactDao!!.remove(id)
    }
}