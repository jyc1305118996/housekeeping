package com.haige.aop;

import com.haige.annotation.PermissionType;

import com.haige.filter.ReactiveRequestContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Aspect
@Component
@Slf4j
public class WebLogAspect {


    /**
     * 1）execution(public * *(..))——表示匹配所有public方法
     * 2）execution(* set*(..))——表示所有以“set”开头的方法
     * 3）execution(* com.xyz.service.AccountService.*(..))——表示匹配所有AccountService接口的方法
     * 4）execution(* com.xyz.service.*.*(..))——表示匹配service包下所有的方法
     * 5）execution(* com.xyz.service..*.*(..))——表示匹配service包和它的子包下的方法
     */
    @Pointcut("execution(public * com.haige.web.controller..*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        ReactiveRequestContextHolder
                .getRequest()
                .subscribe(request -> {
                    // 记录下请求内容
                    log.info("URL : " + request.getURI());
                    log.info("HTTP_METHOD : " + request.getMethod());
                    log.info("IP : " + request.getRemoteAddress());
                    MultiValueMap<String, String> queryParams = request.getQueryParams();
                    queryParams.forEach((name, value) -> {
                        log.info("name:{},value:{}", name, value);
                    });
                });

        //无需任何判断

//        if(PermissionEnum.ALL.equals(pms.value())){
//
//        }
//        //需要判断登陆的
//        if (PermissionEnum.LOGIN.equals(pms.value())){
//
//        }
//        //需要判断是不是管理员的
//        if(PermissionEnum.ADMIN.equals(pms.value())){
//
//        }

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("RESPONSE : {}", ret);
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
public static void main(String[] args) {
    System.out.println(PermissionType.ALL);
}
}
