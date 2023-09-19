package com.bartoszjaszczak.security.user

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

@Aspect
class UserInfoAspect {

    @Autowired
    private lateinit var userProvider: UserProvider

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Before("@annotation(com.bartoszjaszczak.security.user.UserInfo) && execution(* *(..))")
    fun logExecutionTime(joinPoint: JoinPoint) {
        val currentUser = userProvider.currentUser() ?: "Unknown"
        logger.info("User: ${currentUser}, accessing method ${joinPoint.signature}",)
    }
}