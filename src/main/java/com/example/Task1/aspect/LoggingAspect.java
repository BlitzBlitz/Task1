package com.example.Task1.aspect;


import com.example.Task1.Task1Application;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(Task1Application.class);

    @Around("execution (* com.example.Task1.service.*.*(..))")
    public void loggAllMethods(ProceedingJoinPoint pJoinPoint) throws Throwable{

        String className = pJoinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = pJoinPoint.getSignature().getName();

        final StopWatch stopWatch = new StopWatch();

        LOGGER.info("Executing " + className + "." + methodName);
        stopWatch.start();
        stopWatch.stop();

        LOGGER.info("Execution time of " + className + "." + methodName + " is " + stopWatch.getTotalTimeMillis() + " ms");

    }
}
