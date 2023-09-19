package com.bartoszjaszczak

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}