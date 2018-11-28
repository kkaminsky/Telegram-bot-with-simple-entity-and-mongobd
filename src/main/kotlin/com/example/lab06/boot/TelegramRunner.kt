package com.example.lab06.boot

import com.example.lab06.service.TelegramBot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.exceptions.TelegramApiException


@Component
class TelegramRunner @Autowired constructor(
        private val bot: TelegramBot
): CommandLineRunner {

    override fun run(vararg args: String?) {
        val telegramBotsApi = TelegramBotsApi()

        try {
            telegramBotsApi.registerBot(bot)
        }catch (e: TelegramApiException){
            e.printStackTrace()
        }
    }

}