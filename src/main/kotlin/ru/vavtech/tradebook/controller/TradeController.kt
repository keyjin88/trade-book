package ru.vavtech.tradebook.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vavtech.tradebook.dto.CreateTradeRequest
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
    
    @PostMapping
    fun createTrade(@RequestBody request: CreateTradeRequest): ResponseEntity<Trade> {
        val trade = Trade(
            id = "", // будет генерироваться в сервисе
            symbol = request.symbol,
            side = request.side,
            quantity = request.quantity,
            price = request.price,
            commission = request.commission,
            timestamp = LocalDateTime.now(), // будет перезаписан в сервисе
            notes = request.notes
        )
        
        val createdTrade = tradeService.addTrade(trade)
        return ResponseEntity.ok(createdTrade)
    }
    
    @GetMapping("/summary")
    fun getTradesSummary(): Map<String, Any> {
        return tradeService.getTradesSummary()
    }
} 