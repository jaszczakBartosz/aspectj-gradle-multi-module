package com.bartoszjaszczak.logger


import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.StopWatch


@Aspect
class LoggerAspect() {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Around("@annotation(com.bartoszjaszczak.logger.Log)")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any? {
        val stopWatch = StopWatch()
        stopWatch.start()
        val proceed = joinPoint.proceed()
        stopWatch.stop()
        logger.info("${joinPoint.signature} executed in ${stopWatch.totalTimeMillis} ms")
        return proceed
    }
}