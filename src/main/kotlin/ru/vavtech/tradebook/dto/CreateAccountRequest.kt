package ru.vavtech.tradebook.dto

import java.math.BigDecimal

data class CreateAccountRequest(
    val name: String,
    val broker: String? = null,
    val accountType: String? = null,
    val currency: String? = null,
    val initialBalance: BigDecimal? = null,
    val notes: String? = null
) 