package ru.vavtech.tradebook.dto

import ru.vavtech.tradebook.model.TradeSide
import java.math.BigDecimal
import java.time.LocalDateTime

data class CreateTradeRequest(
    val symbol: String,
    val side: TradeSide,
    val quantity: BigDecimal,
    val entryPrice: BigDecimal,
    val entryTime: LocalDateTime? = null,
    val takeProfit: BigDecimal? = null,
    val stopLoss: BigDecimal? = null,
    val commission: BigDecimal = BigDecimal.ZERO,
    val notes: String = ""
) 