package com.example.L3.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.example.L3.controller.EmployeeController.createEmployee(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("The Create employee method "+joinPoint.getSignature().getName() +" took "+ elapsedTime + " milliseconds to execute");
        return result;
    }

}
