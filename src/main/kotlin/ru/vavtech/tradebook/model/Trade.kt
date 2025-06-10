package ru.vavtech.tradebook.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Trade(
    val id: String,
    val symbol: String,
    val side: TradeSide,
    val quantity: BigDecimal,
    val entryPrice: BigDecimal,
    val exitPrice: BigDecimal? = null,
    val entryTime: LocalDateTime,
    val exitTime: LocalDateTime? = null,
    val takeProfit: BigDecimal? = null,
    val stopLoss: BigDecimal? = null,
    val commission: BigDecimal = BigDecimal.ZERO,
    val profit: BigDecimal? = null,
    val status: TradeStatus = TradeStatus.OPEN,
    val notes: String = ""
)

enum class TradeSide {
    BUY, SELL
}

enum class TradeStatus {
    OPEN, CLOSED
} 