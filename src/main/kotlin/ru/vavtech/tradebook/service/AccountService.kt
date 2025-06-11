package ru.vavtech.tradebook.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import ru.vavtech.tradebook.model.Account
import java.io.File
import java.time.LocalDateTime
import java.util.*

@Service
class AccountService {
    
    private val dataFile = File("accounts.json")
    private val objectMapper: ObjectMapper = jacksonObjectMapper().apply {
        registerModule(JavaTimeModule())
    }
    
    fun getAllAccounts(): List<Account> {
        return if (dataFile.exists()) {
            try {
                objectMapper.readValue<List<Account>>(dataFile)
            } catch (e: Exception) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }
    
    fun getAccount(id: String): Account? {
        return getAllAccounts().find { it.id == id }
    }
    
    fun addAccount(account: Account): Account {
        val accounts = getAllAccounts().toMutableList()
        val newAccount = account.copy(
            id = UUID.randomUUID().toString(),
            createdAt = LocalDateTime.now()
        )
        accounts.add(newAccount)
        saveAccounts(accounts)
        return newAccount
    }
    
    fun updateAccount(id: String, updates: Map<String, Any?>): Account? {
        val accounts = getAllAccounts().toMutableList()
        val accountIndex = accounts.indexOfFirst { it.id == id }
        
        if (accountIndex == -1) return null
        
        val existingAccount = accounts[accountIndex]
        val updatedAccount = existingAccount.copy(
            name = updates["name"] as? String ?: existingAccount.name,
            broker = updates["broker"] as? String ?: existingAccount.broker,
            accountType = updates["accountType"] as? String ?: existingAccount.accountType,
            currency = updates["currency"] as? String ?: existingAccount.currency,
            initialBalance = updates["initialBalance"] as? java.math.BigDecimal ?: existingAccount.initialBalance,
            isActive = updates["isActive"] as? Boolean ?: existingAccount.isActive,
            notes = updates["notes"] as? String ?: existingAccount.notes
        )
        
        accounts[accountIndex] = updatedAccount
        saveAccounts(accounts)
        return updatedAccount
    }
    
    fun deleteAccount(id: String): Boolean {
        val accounts = getAllAccounts().toMutableList()
        val accountIndex = accounts.indexOfFirst { it.id == id }
        
        if (accountIndex == -1) return false
        
        accounts.removeAt(accountIndex)
        saveAccounts(accounts)
        return true
    }
    
    fun getActiveAccounts(): List<Account> {
        return getAllAccounts().filter { it.isActive }
    }
    
    private fun saveAccounts(accounts: List<Account>) {
        objectMapper.writeValue(dataFile, accounts)
    }
    
    fun createDefaultAccountIfNone(): Account? {
        val accounts = getAllAccounts()
        if (accounts.isEmpty()) {
            val defaultAccount = Account(
                id = "",
                name = "Основной счет",
                broker = "",
                accountType = "Demo",
                currency = "USD",
                initialBalance = java.math.BigDecimal.ZERO,
                isActive = true,
                notes = "Автоматически созданный счет"
            )
            return addAccount(defaultAccount)
        }
        return null
    }
} 