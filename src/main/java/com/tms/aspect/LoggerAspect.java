package com.tms.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;

import java.time.LocalTime;

public class LoggerAspect {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Before("within (com.tms.*)")
    public void getLogBefore(JoinPoint joinPoint) {
        logger.info("Method " + joinPoint.getSignature() + " started");
    }

    @After("within (com.tms.*)")
    public void getLogAfter(JoinPoint joinPoint) {
        logger.info("Method " + joinPoint.getSignature() + " finished");
    }

    @Around("within (com.tms.*)")
    public void getTime(ProceedingJoinPoint proceedingJoinPoint, JoinPoint joinPoint) throws Throwable {
        LocalTime start = LocalTime.now();
        proceedingJoinPoint.proceed();
        LocalTime finish = LocalTime.now();
        logger.info("Method " + joinPoint.getSignature() + " work time is: " + (finish.getNano()-start.getNano()));

    }
}
