package com.keruyun.gateway.validation.annotation.aop;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.keruyun.gateway.validation.annotation.ValidateMapping;
import com.keruyun.gateway.validation.annotation.Validator;
import com.keruyun.gateway.validation.exception.ValidationException;
import com.keruyun.gateway.validation.request.Request;
import com.keruyun.gateway.validation.response.Response;
import com.keruyun.gateway.validation.type.ErrorType;
import com.keruyun.gateway.validation.type.SignModelType;
import com.keruyun.gateway.validation.utils.GsonWrapper;
import com.keruyun.gateway.validation.utils.RequestUtils;
import com.keruyun.gateway.validation.utils.ResponseUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数验证拦截器
 *
 * @author youngtan99@163.com
 * @since 2015年4月7日
 */
@Aspect
public class ValidatorAspect {
    /**
     *
     */
    public static Logger logger = LoggerFactory.getLogger(ValidatorAspect.class);

    /**
     *
     */
    private Gson gson = GsonWrapper.GSON;

    /**
     * 签名模式
     * 默认签名模式为DEBUG模式
     */
    private String defaultSignModeType = SignModelType.RUN.getKey();

    public String getDefaultSignModeType() {
        return defaultSignModeType;
    }

    public void setDefaultSignModeType(String defaultSignModeType) {
        this.defaultSignModeType = defaultSignModeType;
    }

    @Pointcut("@annotation(com.keruyun.gateway.validation.annotation.Validator)")
    private void validatorPointcut() {
    }


    @AfterThrowing(pointcut = "validatorPointcut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        try {
            StringBuffer params = new StringBuffer();
            Object[] objects = joinPoint.getArgs();
            if (objects != null && objects.length > 0) {
                for (Object object : objects) {
                    params.append(object.getClass().getName())
                            .append(" : ")
                            .append(gson.toJson(object))
                            .append(" | ");
                }
            }
            String message = getMethodName(joinPoint) + " : " + params.toString();
            logger.info("request parameters : {}  ", message);
        } catch (Exception ex) {
            // 记录本地异常日志
            logger.error("aop throwing exception", e);
        }
    }


    /**
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("validatorPointcut()")
    public Object validateAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Response<?> response = new Response<Object>();
        Map<String, Object> map = getValidator(joinPoint);
        // 校验签名
        validateSign(response, map, joinPoint.getArgs());
        // 校验参数
        getValidateResponse(response, map, joinPoint.getArgs());
        // 判断返回是否成功
        if (response.getReturnCode() == 0) {
            return joinPoint.proceed();
        } else {
            Class returnType = getMethodByClassAndName(joinPoint.getTarget().getClass(),
                    joinPoint.getSignature().getName()).getReturnType();
            if (returnType == Response.class) { // 返回response
                return response;
            }
        }
        return null;
    }

    /**
     * 签名校验
     *
     * @param response
     * @param map
     */
    private void validateSign(Response<?> response, Map<String, Object> map, Object[] args) {
        boolean isSign = false;
        if (map.containsKey("validator")) {
            isSign = ((Validator) map.get("validator")).isSign();
        }
        // 不做签名校验
        if (isSign) {
            try {
                if (args != null && args.length > 0) {
                    for (Object arg : args) {
                        if (arg instanceof Request) {// 如果是Request类型
                            RequestUtils.isSign((Request) arg, SignModelType.getType(this.defaultSignModeType.toUpperCase()));
                        }
                    }
                }
            } catch (ValidationException e) {
                //签名异常
                logger.error("签名异常", e);
                ResponseUtils.getResponse(e, response);
                return;
            } catch (JsonSyntaxException e) {
                logger.error("签名json数据转换异常", e);
                response.setResponseMessage(ErrorType.SYSTEM_ERROR, "json数据转换异常");
                return;
            }
        }
    }

    /**
     * 参数校验
     *
     * @param response
     * @param map
     * @param args
     * @return
     * @throws Exception
     */
    protected Response<?> getValidateResponse(Response<?> response, Map<String, Object> map, Object[] args) {
        if (map.containsKey("method")) {
            Method method = (Method) map.get("method");
            Annotation[][] ans = method.getParameterAnnotations();
            for (int i = 0; i < ans.length; i++) {
                Object obj = args[i];
                for (int j = 0; j < ans[i].length; j++) {
                    Annotation annotation = ans[i][j];
                    if (annotation instanceof ValidateMapping) {
                        try {
                            RequestUtils.checkParameters(obj);
                        } catch (ValidationException e) { // 异常处理
                            return ResponseUtils.getResponse(e, response);
                        }
                    }
                }
            }
        }
        return response;
    }


    /**
     * 获取注解
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    protected Map<String, Object> getValidator(JoinPoint joinPoint) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    map.put("validator", method.getAnnotation(Validator.class));
                    map.put("method", method);
                    return map;
                }
            }
        }
        return map;
    }

    /**
     * 根据类和方法名得到方法
     */
    public Method getMethodByClassAndName(Class<?> c, String methodName) {
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName) && method.getReturnType() == Response.class) {
                return method;
            }
        }
        return null;
    }

    protected String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName()
                + "()";
    }
}
