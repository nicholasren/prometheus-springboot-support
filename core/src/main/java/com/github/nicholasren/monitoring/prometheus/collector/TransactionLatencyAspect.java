package com.github.nicholasren.monitoring.prometheus.collector;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class TransactionLatencyAspect {

    public TransactionLatencyAspect(TransactionLatencyCollector collector) {
        this.collector = collector;
    }

    private TransactionLatencyCollector collector;

    @Pointcut("within(@com.github.nicholasren.monitoring.prometheus.annotations.MonitoredController *)")
    public void targetBean() {
    }

    @Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..))")
    public void mappedMethod() {
    }

    @Around("targetBean() && mappedMethod()")
    public Object measure(ProceedingJoinPoint pjp) throws Throwable {
        return collector.measure(pjp);
    }
}
