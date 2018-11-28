package com.example.lab06.service

import com.example.lab06.entity.TelegramUser

interface TelegramUserService {

    fun resetStatus(telegramUserId: Int)

    fun getAction(telegramUserId: Int):String

    fun getTable(telegramUserId: Int):String

    fun getStatus(telegramUserId: Int): Int

    fun updateTable(telegramUserId: Int,table:String)

    fun updateAction(telegramUserId: Int,action:String)
}