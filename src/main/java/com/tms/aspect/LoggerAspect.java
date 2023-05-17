package com.tms.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;

@Aspect
@Component
public class LoggerAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.tms.*.*.*(..))")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        LocalTime start = LocalTime.now();
        Object proceed = proceedingJoinPoint.proceed();
        LocalTime finish = LocalTime.now();
        int time = finish.getNano() - start.getNano();
        String name = proceedingJoinPoint.getSignature().toString();
        logger.info("Method execution " + name + " with arguments: " + Arrays.toString(args) + ". Work time is: " + time);
        return proceed;
    }

    @AfterThrowing(value = "execution(* com.tms.*.*.*(..))", throwing = "error")
    public void getLogAfterThrowing(Throwable error) {
        logger.warn("There is an error: " + error);
    }
}