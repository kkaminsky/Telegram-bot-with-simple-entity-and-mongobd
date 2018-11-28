package com.example.lab06.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "telegramUsers")
class TelegramUser {

    @Id
    var id: String? = null

    var telegramUserId: Int=0

    var step:Int?=null

    var action:String?=null

    var table:String?=null
}