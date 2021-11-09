package ru.geekbrains.myspringshop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatisticAspect {
    private long startTime;
    private long duration;

 /*   @Before("@annotation(statistic)")
    public Object beforStart(Statistic statistic){
        startTime = System.currentTimeMillis();
        return null;
    }

    @After("@annotation(statistic)")
    public Object afterEnd( Statistic statistic){
        duration = System.currentTimeMillis() - startTime;
        System.out.println("method executed in " + duration + " ms");
        return null;
    }*/


   // @Around("within(ru.geekbrains.myspringshop.service.*)")
    @Around("@annotation(statistic)")
    public Object aroundCallAt(ProceedingJoinPoint joinPoint, Statistic statistic) throws Throwable {
        final long startTime = System.currentTimeMillis();
        Object retrieveValue = null;
        try {
            retrieveValue = joinPoint.proceed();
        }finally{
           duration = System.currentTimeMillis() - startTime;
            joinPoint.getSignature().getName();
            joinPoint.getSourceLocation().getWithinType().getName();
            System.out.println("method "+ joinPoint.getSignature().getName() +", class " +
                               joinPoint.getSourceLocation().getWithinType().getName()+ ", execution time: " + duration + "ms");
            return retrieveValue;
        }
    }
}
