package com.bartoszjaszczak.api

import com.bartoszjaszczak.account.AccountService
import com.bartoszjaszczak.logger.Log
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("api/account")
class AccountApi(private val accountService: AccountService) {
    @Log
    @GetMapping("balance")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun getAccountBalance(principal: Principal) = accountService.userBalance(principal.name)
}