package com.kernel360.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around("@annotation(com.kernel360.global.annotation.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object proceed = joinPoint.proceed();

        long executionTime = System.nanoTime() - start;
        log.info("##### @LogExecutionTime ##### " + joinPoint.getSignature() + " executed in " + executionTime + "ms");

        return proceed;
    }

    @Pointcut("within(*..*Controller)")
    public void controller() {}

    @Around("controller()")
    public Object logApiExecTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object proceed = joinPoint.proceed();

        long executionTime = System.nanoTime() - start;
        Signature signature = joinPoint.getSignature();

        log.info(String.format("##### @API Execution Time ##### [%dms] → %s.%s",
                executionTime, signature.getDeclaringTypeName(), signature.getName()));

        return proceed;
    }
}