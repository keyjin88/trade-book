package ru.vavtech.tradebook.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.vavtech.tradebook.service.TradeService
import ru.vavtech.tradebook.service.AccountService

@Controller
class WebController(
    private val tradeService: TradeService,
    private val accountService: AccountService
) {
    
    @GetMapping("/")
    fun index(@RequestParam(required = false) accountId: String?, model: Model): String {
        // Создаем счет по умолчанию, если их нет
        accountService.createDefaultAccountIfNone()
        
        val accounts = accountService.getActiveAccounts()
        val currentAccountId = accountId ?: accounts.firstOrNull()?.id
        
        if (currentAccountId != null) {
            model.addAttribute("trades", tradeService.getTradesByAccount(currentAccountId))
            model.addAttribute("summary", tradeService.getTradesSummaryByAccount(currentAccountId))
        } else {
            model.addAttribute("trades", emptyList<ru.vavtech.tradebook.model.Trade>())
            model.addAttribute("summary", mapOf(
                "totalTrades" to 0,
                "profitablePercentage" to 0.0,
                "totalProfit" to java.math.BigDecimal.ZERO
            ))
        }
        
        model.addAttribute("accounts", accounts)
        model.addAttribute("currentAccountId", currentAccountId)
        return "index"
    }
} 