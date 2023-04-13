package com.tms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Aspect
@Component
public class LoggerAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("within (com.tms.*)")
    public void getLogBefore(JoinPoint joinPoint) {
        logger.info("Method " + joinPoint.getSignature() + " started");
    }

    @After("within (com.tms.*)")
    public void getLogAfter(JoinPoint joinPoint) {
        logger.info("Method " + joinPoint.getSignature() + " finished");
    }

    @Around("within (com.tms.*)")
    public void getTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        LocalTime start = LocalTime.now();
        proceedingJoinPoint.proceed();
        LocalTime finish = LocalTime.now();
        logger.info("Method " + proceedingJoinPoint.getSignature() + " work time is: " + (finish.getNano()-start.getNano()));
    }
    @AfterThrowing(value = "within(com.tms.*)", throwing = "error")
    public void getLogAfterThrowing(Throwable error) {
        logger.warn("We have trouble! " + error);
    }
}
