package ru.vavtech.tradebook.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.vavtech.tradebook.dto.CreateAccountRequest
import ru.vavtech.tradebook.model.Account
import ru.vavtech.tradebook.service.AccountService
import java.math.BigDecimal

@RestController
@RequestMapping("/api/accounts")
class AccountController(private val accountService: AccountService) {
    
    @GetMapping
    fun getAllAccounts(): List<Account> {
        return accountService.getAllAccounts()
    }
    
    @GetMapping("/active")
    fun getActiveAccounts(): List<Account> {
        return accountService.getActiveAccounts()
    }
    
    @GetMapping("/{id}")
    fun getAccount(@PathVariable id: String): ResponseEntity<Account> {
        val account = accountService.getAccount(id)
        return if (account != null) {
            ResponseEntity.ok(account)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @PostMapping
    fun createAccount(@RequestBody request: CreateAccountRequest): ResponseEntity<Account> {
        val account = Account(
            id = "",
            name = request.name,
            broker = request.broker ?: "",
            accountType = request.accountType ?: "",
            currency = request.currency ?: "USD",
            initialBalance = request.initialBalance ?: BigDecimal.ZERO,
            isActive = true,
            notes = request.notes ?: ""
        )
        
        val createdAccount = accountService.addAccount(account)
        return ResponseEntity.ok(createdAccount)
    }
    
    @PutMapping("/{id}")
    fun updateAccount(@PathVariable id: String, @RequestBody updates: Map<String, Any?>): ResponseEntity<Account> {
        val updatedAccount = accountService.updateAccount(id, updates)
        return if (updatedAccount != null) {
            ResponseEntity.ok(updatedAccount)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @DeleteMapping("/{id}")
    fun deleteAccount(@PathVariable id: String): ResponseEntity<Void> {
        val deleted = accountService.deleteAccount(id)
        return if (deleted) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
} 