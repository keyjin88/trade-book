package ru.vavtech.tradebook.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import ru.vavtech.tradebook.model.Trade
import java.io.File
import java.time.LocalDateTime
import java.util.*

@Service
class TradeService {
    
    private val dataFile = File("trades.json")
    private val objectMapper: ObjectMapper = jacksonObjectMapper().apply {
        registerModule(JavaTimeModule())
    }
    
    fun getAllTrades(): List<Trade> {
        return if (dataFile.exists()) {
            try {
                objectMapper.readValue<List<Trade>>(dataFile)
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }
    
    fun addTrade(trade: Trade): Trade {
        val trades = getAllTrades().toMutableList()
        val newTrade = trade.copy(
            id = UUID.randomUUID().toString(),
            timestamp = LocalDateTime.now()
        )
        trades.add(newTrade)
        saveTrades(trades)
        return newTrade
    }
    
    private fun saveTrades(trades: List<Trade>) {
        objectMapper.writeValue(dataFile, trades)
    }
    
    fun getTradesSummary(): Map<String, Any> {
        val trades = getAllTrades()
        val totalTrades = trades.size
        val totalVolume = trades.sumOf { it.quantity * it.price }
        val totalCommissions = trades.sumOf { it.commission }
        
        return mapOf(
            "totalTrades" to totalTrades,
            "totalVolume" to totalVolume,
            "totalCommissions" to totalCommissions
        )
    }
} 