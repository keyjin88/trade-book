package ru.vavtech.tradebook.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vavtech.tradebook.dto.CreateTradeRequest
import ru.vavtech.tradebook.dto.UpdateTradeRequest
import ru.vavtech.tradebook.model.Trade
import ru.vavtech.tradebook.service.TradeService
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/trades")
class TradeController(private val tradeService: TradeService) {
    
    @GetMapping
    fun getAllTrades(): List<Trade> {
        return tradeService.getAllTrades()
    }
    
    @GetMapping("/{id}")
    fun getTrade(@PathVariable id: String): ResponseEntity<Trade> {
        val trade = tradeService.getTrade(id)
        return if (trade != null) {
            ResponseEntity.ok(trade)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @PostMapping
    fun createTrade(@RequestBody request: CreateTradeRequest): ResponseEntity<Trade> {
        val trade = Trade(
            id = "", // будет генерироваться в сервисе
            symbol = request.symbol,
            side = request.side,
            quantity = request.quantity,
            entryPrice = request.entryPrice,
            entryTime = request.entryTime ?: LocalDateTime.now(),
            takeProfit = request.takeProfit,
            stopLoss = request.stopLoss,
            commission = request.commission,
            notes = request.notes
        )
        
        val createdTrade = tradeService.addTrade(trade)
        return ResponseEntity.ok(createdTrade)
    }
    
    @PutMapping("/{id}")
    fun updateTrade(@PathVariable id: String, @RequestBody request: UpdateTradeRequest): ResponseEntity<Trade> {
        val updates = mutableMapOf<String, Any?>()
        
        request.exitPrice?.let { updates["exitPrice"] = it }
        request.exitTime?.let { updates["exitTime"] = it }
        request.takeProfit?.let { updates["takeProfit"] = it }
        request.stopLoss?.let { updates["stopLoss"] = it }
        request.commission?.let { updates["commission"] = it }
        request.profit?.let { updates["profit"] = it }
        request.status?.let { updates["status"] = it }
        request.notes?.let { updates["notes"] = it }
        
        val updatedTrade = tradeService.updateTrade(id, updates)
        return if (updatedTrade != null) {
            ResponseEntity.ok(updatedTrade)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @DeleteMapping("/{id}")
    fun deleteTrade(@PathVariable id: String): ResponseEntity<Void> {
        val deleted = tradeService.deleteTrade(id)
        return if (deleted) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/summary")
    fun getTradesSummary(): Map<String, Any> {
        return tradeService.getTradesSummary()
    }
} 