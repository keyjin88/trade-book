package ru.vavtech.tradebook.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Account(
    val id: String,
    val name: String,
    val broker: String = "",
    val accountType: String = "",
    val currency: String = "USD",
    val initialBalance: BigDecimal = BigDecimal.ZERO,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val isActive: Boolean = true,
    val notes: String = ""
) 