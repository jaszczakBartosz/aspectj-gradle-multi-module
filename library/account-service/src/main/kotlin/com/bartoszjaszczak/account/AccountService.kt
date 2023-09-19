package com.bartoszjaszczak.account

import com.bartoszjaszczak.security.user.UserInfo

class AccountService {

    private val userAccount = mapOf("admin" to 1000)

    fun userBalance(username: String) = internalBalance(username)

    @UserInfo
    private fun internalBalance(username: String) = userAccount[username]
}