package ru.vavtech.tradebook.dto

import ru.vavtech.tradebook.model.TradeSide
import java.math.BigDecimal

data class CreateTradeRequest(
    val symbol: String,
    val side: TradeSide,
    val quantity: BigDecimal,
    val price: BigDecimal,
    val commission: BigDecimal = BigDecimal.ZERO,
    val notes: String = ""
) 