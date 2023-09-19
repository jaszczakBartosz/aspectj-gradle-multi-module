package com.bartoszjaszczak.logger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoggerAspectConfig {

    @Bean
    fun loggerAspect() = LoggerAspect()
}