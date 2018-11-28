package com.example.lab06.service

import com.example.lab06.dao.TelegramUserDao
import com.example.lab06.entity.TelegramUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TelegramUserServiceImpl @Autowired constructor(
        private val telegramUserDao: TelegramUserDao)
    :TelegramUserService
{
    override fun getStatus(telegramUserId: Int): Int {
        val user = telegramUserDao.getByTelegramUserId(telegramUserId)
        if (user != null)
            return user.step!!
        else
            return 0
    }

    override fun updateTable(telegramUserId: Int, table: String) {
        var telegramUser = telegramUserDao.getByTelegramUserId(telegramUserId)
        if (telegramUser == null) {
            telegramUser = TelegramUser()
            telegramUser.telegramUserId = telegramUserId
            telegramUser.table = table
            telegramUser.step = 1
            telegramUserDao.save(telegramUser)
        } else {
            telegramUser.step = 1
            telegramUser.table = table
            telegramUserDao.save(telegramUser)
        }

    }

    override fun updateAction(telegramUserId: Int, action: String) {
        var telegramUser = telegramUserDao.getByTelegramUserId(telegramUserId)
        if (telegramUser!=null){
            telegramUser.action=action
            telegramUser.step = 2
            telegramUserDao.save(telegramUser)
        }
    }

    override fun getTable(telegramUserId: Int): String {

        val user = telegramUserDao.getByTelegramUserId(telegramUserId)
        return user!!.table!!

    }

    override fun getAction(telegramUserId: Int): String {
        val user = telegramUserDao.getByTelegramUserId(telegramUserId)
        return user!!.action!!
    }

    override fun resetStatus(telegramUserId: Int) {
        val user = telegramUserDao.getByTelegramUserId(telegramUserId)
        user!!.step=0
        telegramUserDao.save(user)
    }
}