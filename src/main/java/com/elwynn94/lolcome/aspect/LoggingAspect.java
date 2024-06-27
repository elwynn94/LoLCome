package com.elwynn94.lolcome.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private final HttpServletRequest request;

    public LoggingAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {}

    @Before("controller()")
    public void logBefore() {

        if (request == null) {
            log.warn("RequestAttributes가 null 입니다!");
        }
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        log.info("Request URL: {}, HTTP Method: {}", url, method);
    }

    @AfterReturning(pointcut = "controller()", returning = "result")
    public void logAfter(Object result) {
        log.info("Response: {}", result);
    }
}
