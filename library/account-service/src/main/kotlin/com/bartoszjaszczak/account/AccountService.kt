package com.bartoszjaszczak.account

class AccountService {

    private val userAccount = mapOf("testUser" to 1000)

    fun userAccount(username: String) = userAccount[username]
}