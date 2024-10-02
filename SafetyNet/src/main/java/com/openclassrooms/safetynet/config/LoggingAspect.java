package com.openclassrooms.safetynet.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Log avant l'exécution d'une méthode dans les packages 'service' ou 'dao'
    @Before("execution(* com.openclassrooms.safetynet.service..*(..)) || execution(* com.openclassrooms.safetynet.dao..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    // Log après le retour d'une méthode dans les packages 'service' ou 'dao'
    @AfterReturning(pointcut = "execution(* com.openclassrooms.safetynet.service..*(..)) || execution(* com.openclassrooms.safetynet.dao..*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("Exiting method: {} with result: {}", joinPoint.getSignature(), result);
    }
}
