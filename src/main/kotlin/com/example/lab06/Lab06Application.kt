package com.example.lab06

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.telegram.telegrambots.ApiContextInitializer

@SpringBootApplication
//@EnableJpaRepositories(repositoryBaseClass = TelegramRunner::class)
class Lab06Application

fun main(args: Array<String>) {
    ApiContextInitializer.init()
    SpringApplication.run(Lab06Application::class.java, *args)
}
