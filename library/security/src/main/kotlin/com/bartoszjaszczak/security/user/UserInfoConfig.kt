package com.bartoszjaszczak.security.user

import org.aspectj.lang.Aspects
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserInfoConfig {

    @Bean
    fun userInfoAspect() = Aspects.aspectOf(UserInfoAspect::class.java)

    @Bean
    fun userProvider() = UserProvider()
}