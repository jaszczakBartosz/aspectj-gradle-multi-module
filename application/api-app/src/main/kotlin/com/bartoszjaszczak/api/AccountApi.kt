package com.bartoszjaszczak.api

import com.bartoszjaszczak.account.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("api/account")
class AccountApi(private val accountService: AccountService) {

    @GetMapping("balance")
    fun getAccountBalance(principal: Principal) = accountService.userAccount(principal.name)
}