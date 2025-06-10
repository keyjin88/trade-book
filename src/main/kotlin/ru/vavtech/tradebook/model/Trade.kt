package ru.vavtech.tradebook.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Trade(
    val id: String,
    val symbol: String,
    val side: TradeSide,
    val quantity: BigDecimal,
    val price: BigDecimal,
    val commission: BigDecimal = BigDecimal.ZERO,
    val timestamp: LocalDateTime,
    val notes: String = ""
)

enum class TradeSide {
    BUY, SELL
} 