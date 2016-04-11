package com.keruyun.gateway.validation.annotation.aop;


import com.keruyun.gateway.validation.utils.CurlUtils;
import com.keruyun.gateway.validation.utils.GsonWrapper;
import com.keruyun.gateway.validation.utils.RequestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Aop controller 日志监控
 *
 * @author tany@shishike.com
 * @since 2015年4月7日
 */
@Aspect
public class ControllerAspect {

    public  static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    /**
     * 切入点
     *
     * @author tany@shishike.com
     * @2015年4月7日
     */
    @Pointcut("@annotation(com.keruyun.gateway.validation.annotation.CurlLogger)")
    private void controllerPointcut() {
        //TODO
    }


    /**
     * 声明前置通知
     *
     * @param joinPoint
     * @author tany@shishike.com
     * @2015年4月7日
     */
    @Before("controllerPointcut()")
    public void doBefore(JoinPoint joinPoint) {

        HttpServletRequest request = RequestUtils.threadLocalRequest.get();
        // 请求的IP
        StringBuffer params = new StringBuffer();
        Object[] objects = joinPoint.getArgs();
        if (objects != null && objects.length > 0) {
            params.append(GsonWrapper.toJson(objects[0]));
        }
        if (request == null) {
            return;
        }
        try {
            String methodName = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName()
                    + "()";
            logger.info(
                    "Method=[ {} ] , Args=[ {} ]  ",
                    methodName,
                    CurlUtils.jsonCurl(request.getMethod().toUpperCase(), request.getRequestURL().toString(),
                            params.toString()));
        } catch (Exception e) {
            // 记录本地异常日志
            logger.error("Exception Message : {} ", e.getMessage());
        }
    }

    /**
     * @AfterReturning 当一个方法执行返回后，返回值作为相应的参数值传入通知方法。 一个 returning 子句也限制了只能匹配到返回指定类型值的方法。
     * @author tany@shishike.com
     * @2015年4月7日
     */
    @AfterReturning(pointcut = "controllerPointcut()", returning = "retVal")
    public void doAfter(JoinPoint joinPoint, Object retVal) {
        try {
            String methodName = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName()
                    + "()";
            logger.info("Method=[ {} ] , Return=[ {} ]", methodName, GsonWrapper.toJson(retVal));
        } catch (Exception e) {
            // 记录本地异常日志
            logger.error("Exception Message : {} ", e.getMessage());
        }
    }

}
