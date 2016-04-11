package com.keruyun.gateway.validation.utils;

import com.google.gson.Gson;
import com.keruyun.gateway.validation.exception.ServiceException;
import com.keruyun.gateway.validation.exception.ValidationException;
import com.keruyun.gateway.validation.response.Response;
import org.apache.commons.lang3.StringUtils;

/**
 * 请求响应帮助类
 *
 * @author tany@shishike.com
 * @since 2015年2月26日
 */
public class ResponseUtils {

    private final static String DAO_EXCEPTION = "DaoException";


    /**
     * 校验异常 response 处理
     *
     * @param e
     * @param response
     * @return
     * @author tany@shishike.com
     * @2015年2月26日
     */
    public static <T> Response<T> getResponse(ValidationException e, Response<T> response) {
        response.setResponseMessage(e.getErrorType(), dealDaoException(e));
        return response;
    }

    /**
     * service 异常 response 处理
     *
     * @param e
     * @param response
     * @param <T>
     * @return
     */
    public static <T> Response<T> getResponse(ServiceException e, Response<T> response) {
        response.setResponseMessage(e.getErrorType(), dealDaoException(e));
        return response;
    }


    /**
     * 消息处理
     *
     * @param e
     * @return
     * @author tany@shishike.com
     * @2015年2月27日
     */
    private static String dealDaoException(Exception e) {
        String message = e.getMessage();
        String msg = "business data access exception";
        if (!StringUtils.contains(message, DAO_EXCEPTION)) {
            msg = message;
        }
        return msg;
    }

    /**
     * 将json 转换为response
     * @param json  json 字符串
     * @param <T>
     * @return
     */
    public static <T> Response<T> getResponse(String json) {
        return GsonWrapper.fromJson(json, Response.class);
    }

}
