package ru.vavtech.tradebook.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import ru.vavtech.tradebook.model.Trade
import java.io.File
import java.math.BigDecimal
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
            entryTime = trade.entryTime ?: LocalDateTime.now()
        )
        trades.add(newTrade)
        saveTrades(trades)
        return newTrade
    }
    
    fun updateTrade(id: String, updates: Map<String, Any?>): Trade? {
        val trades = getAllTrades().toMutableList()
        val tradeIndex = trades.indexOfFirst { it.id == id }
        
        if (tradeIndex == -1) return null
        
        val existingTrade = trades[tradeIndex]
        val updatedTrade = existingTrade.copy(
            exitPrice = updates["exitPrice"] as? BigDecimal ?: existingTrade.exitPrice,
            exitTime = updates["exitTime"] as? LocalDateTime ?: existingTrade.exitTime,
            takeProfit = updates["takeProfit"] as? BigDecimal ?: existingTrade.takeProfit,
            stopLoss = updates["stopLoss"] as? BigDecimal ?: existingTrade.stopLoss,
            commission = updates["commission"] as? BigDecimal ?: existingTrade.commission,
            profit = updates["profit"] as? BigDecimal ?: existingTrade.profit,
            status = updates["status"] as? ru.vavtech.tradebook.model.TradeStatus ?: existingTrade.status,
            notes = updates["notes"] as? String ?: existingTrade.notes
        )
        
        trades[tradeIndex] = updatedTrade
        saveTrades(trades)
        return updatedTrade
    }
    
    fun getTrade(id: String): Trade? {
        return getAllTrades().find { it.id == id }
    }
    
    private fun saveTrades(trades: List<Trade>) {
        objectMapper.writeValue(dataFile, trades)
    }
    
    fun getTradesSummary(): Map<String, Any> {
        val trades = getAllTrades()
        val totalTrades = trades.size
        val tradesWithProfit = trades.filter { it.profit != null }
        val profitableTrades = tradesWithProfit.count { it.profit!! > BigDecimal.ZERO }
        val profitablePercentage = if (tradesWithProfit.isNotEmpty()) {
            (profitableTrades.toDouble() / tradesWithProfit.size * 100)
        } else {
            0.0
        }
        val grossProfit = trades.mapNotNull { it.profit }.sumOf { it }
        val totalCommissions = trades.sumOf { it.commission.abs() }
        // Всегда вычитаем комиссии из прибыли, независимо от того как их ввели
        val netProfit = grossProfit - totalCommissions
        
        return mapOf(
            "totalTrades" to totalTrades,
            "profitablePercentage" to profitablePercentage,
            "totalProfit" to netProfit
        )
    }
    
    fun deleteTrade(id: String): Boolean {
        val trades = getAllTrades().toMutableList()
        val tradeIndex = trades.indexOfFirst { it.id == id }
        
        if (tradeIndex == -1) return false
        
        trades.removeAt(tradeIndex)
        saveTrades(trades)
        return true
    }
} 