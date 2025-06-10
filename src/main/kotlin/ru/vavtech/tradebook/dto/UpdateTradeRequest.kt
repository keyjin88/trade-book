package ru.vavtech.tradebook.dto

import ru.vavtech.tradebook.model.TradeStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class UpdateTradeRequest(
    val exitPrice: BigDecimal? = null,
    val exitTime: LocalDateTime? = null,
    val takeProfit: BigDecimal? = null,
    val stopLoss: BigDecimal? = null,
    val commission: BigDecimal? = null,
    val profit: BigDecimal? = null,
    val status: TradeStatus? = null,
    val notes: String? = null
) 