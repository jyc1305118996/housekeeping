package com.haige.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * org.xftm.app.appctrl.*.*(..))")
    public void webLog(){

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            logger.info("name:{},value:{}", name, request.getParameterValues(name));
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }

/*
    @Around("webLog()")
    public void doAround(ProceedingJoinPoint joinPoint) {
        // 拦截的实体类
        Object target = joinPoint.getTarget();
        // 拦截的方法名称。当前正在执行的方法
        String methodName = joinPoint.getSignature().getName();
        // 拦截的方法参数
        Object[] args = joinPoint.getArgs();
        // 拦截的放参数类型
        Signature sig = joinPoint.getSignature();
        // 获得被拦截的方法
        Method method = null;
        MethodSignature msig = (MethodSignature) sig;
        Class[] parameterTypes = msig.getMethod().getParameterTypes();
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);
            Long start = System.currentTimeMillis();
            try {
                joinPoint.proceed(args);
            } catch (Throwable e) {
                logger.error("统计" + target.getClass().getName() + "." + method.getName() + "方法执行耗时环绕通知出错", e);
            }
            Long end = System.currentTimeMillis();
            Long time = end - start;
            logger.info("webLong - " + target.getClass().getName() + "." + method.getName() + " - take " + DateUtil.formatTime(time));
        } catch (Exception e) {
            logger.error("获取方法名异常", e);
        }
    }
    */
}
