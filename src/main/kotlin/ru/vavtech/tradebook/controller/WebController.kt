package ru.vavtech.tradebook.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import ru.vavtech.tradebook.service.TradeService

@Controller
class WebController(private val tradeService: TradeService) {
    
    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("trades", tradeService.getAllTrades())
        model.addAttribute("summary", tradeService.getTradesSummary())
        return "index"
    }
} 