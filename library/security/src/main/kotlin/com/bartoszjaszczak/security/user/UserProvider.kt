package com.bartoszjaszczak.security.user

import org.springframework.security.core.context.SecurityContextHolder

class UserProvider {
    fun currentUser() = SecurityContextHolder.getContext()?.authentication?.principal
}