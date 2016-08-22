package com.example.monitor;

import com.aconex.monitoring.prometheus.collector.TransactionLatencyCollector;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


//Integration point
@Aspect
public class MetricAspect {

    public MetricAspect(TransactionLatencyCollector collector) {
        this.collector = collector;
    }

    private TransactionLatencyCollector collector;

    @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @Pointcut(value = "execution(* com.example.controllers.*Controller.*(..))")
    public void methodPointcut() {
    }

    @Around("requestMapping() && methodPointcut()")
    public Object measure(ProceedingJoinPoint pjp) throws Throwable {
        return collector.measure(pjp);
    }
}
