package com.example.lab06.dao

import com.example.lab06.entity.TelegramUser
import com.example.lab06.entity.TypeOfAdvertising
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class TelegramUserDao @Autowired constructor(
        private val mongoOperations: MongoOperations
) {
    fun save(telegramUser: TelegramUser) {
        mongoOperations.save(telegramUser)
    }

    fun getByTelegramUserId(telegramUserId:Int):TelegramUser? {
        return mongoOperations.findOne(Query.query(Criteria.where("telegramUserId").`is`(telegramUserId)), TelegramUser::class.java)
    }

    fun get(id:String): TelegramUser? {
        return mongoOperations.findOne(Query.query(Criteria.where("id").`is`(id)), TelegramUser::class.java)
    }

    fun getAll(): List<TelegramUser> {
        return mongoOperations.findAll(TelegramUser::class.java)
    }

    fun remove(id: String?) {
        mongoOperations.remove(Query.query(Criteria.where("id").`is`(id!!)), TelegramUser::class.java)
    }

}