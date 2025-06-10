package ru.vavtech.tradebook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TradeBookApplication

fun main(args: Array<String>) {
    runApplication<TradeBookApplication>(*args)
}
