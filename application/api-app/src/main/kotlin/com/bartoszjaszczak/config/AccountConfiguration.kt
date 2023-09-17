package com.bartoszjaszczak.config

import com.bartoszjaszczak.account.AccountService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AccountConfiguration {

    @Bean
    fun accountService() = AccountService()
}