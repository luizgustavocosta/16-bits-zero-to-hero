package com.costa.luiz.zero2hero.business.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingByAspect {

    @Pointcut("within(com.costa.luiz.zero2hero.presentation.web..*)" +
            "|| within(com.costa.luiz.zero2hero.persistence.repository..*)")
    public void packagePointCut() {}

    @Around("packagePointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("In -> {}.{}() with argument -> {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.info("Out -> {}.{}() with the result -> {}",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        result);
            }
            return result;
        } catch (Exception exception) {
            log.error("Exception argument -> {} in {}.{}()",
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
            throw exception;
        }
    }
}
