package com.bartoszjaszczak.config

import org.springframework.context.annotation.AdviceMode
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, mode = AdviceMode.ASPECTJ)
class SecurityConfig {

    @Bean
    fun users(): UserDetailsService {
        val users = User.withDefaultPasswordEncoder()
        val user = users
            .username("user")
            .password("password")
            .roles("USER")
            .build()
        val admin = users
            .username("admin")
            .password("password")
            .roles("USER", "ADMIN")
            .build()
        return InMemoryUserDetailsManager(user, admin)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .authorizeHttpRequests { configurer -> configurer.anyRequest().authenticated() }
            .cors {
                it.disable()
            }
            .httpBasic {}
            .build()
}