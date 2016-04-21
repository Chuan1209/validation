package com.keruyun.gateway.validation.utils;


import com.google.gson.Gson;
import com.keruyun.gateway.validation.exception.ValidationException;
import com.keruyun.gateway.validation.request.Request;
import com.keruyun.gateway.validation.service.ValidationService;
import com.keruyun.gateway.validation.type.ErrorType;
import com.keruyun.gateway.validation.type.SignModelType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Request 帮助类
 *
 * @author youngtan99@163.com
 * @since 2015年1月26日
 */
public class RequestUtils {
    private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);
    private static Gson gson = GsonWrapper.GSON;

    //创建线程
    public final static ThreadLocal<HttpServletRequest> threadLocalRequest = new ThreadLocal<HttpServletRequest>();

    public static void checkParameters(Object obj) throws ValidationException {
        List<String> errors = new ArrayList<String>();
        ValidationService.valid(obj, "", errors);
        if (errors.size() > 0) {
            StringBuffer errs = new StringBuffer();
            for (String error : errors) {
                errs.append(error).append(";");
            }
            throw new ValidationException(ErrorType.PARAM_DATA_ERROR, errs.toString());
        }
    }


    /**
     * @param request
     * @throws ValidationException
     * @author youngtan99@163.com
     * @2015年4月8日
     */
    public static <T> void isSign(Request<T> request, SignModelType signModel) throws ValidationException {
        // 校验签名
        if (signModel == SignModelType.DEBUG) {
            logger.warn("全局签名模式配置为 : {} ", SignModelType.DEBUG.toString());
            return;
        }
        // 入参签名
        if (request.getSignModel().equals(SignModelType.DEBUG.name())) {
            logger.warn("入参签名模式配置为 : {} ", request.getSignModel());
            return;
        }

        if(StringUtils.isBlank(request.getToken())){
            logger.error("Authentication service failed, token is not valid");
            throw new ValidationException(ErrorType.SIGN_ERROR);
        }

        String sign = request.getSign();
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
        treeMap.put("vender", request.getVender());
        treeMap.put("platform", request.getPlatform());
        treeMap.put("device_id", request.getDeviceId());
        treeMap.put("version", request.getVersion());
        treeMap.put("timestamp", request.getTimestamp());
        treeMap.put("content", gson.toJson(request.getRequestBody()));
        String serverSign = String.format("%s&key=%s", treeMap2ascString(treeMap), request.getToken());
        String serverMd5Sign = EncryptUtils.md5(serverSign).toLowerCase();

        logger.info("serverSign = [ {} ] , serverMd5Sign = [ {} ] ", serverSign, serverMd5Sign);
        if (!sign.equals(serverMd5Sign)) {// 签名校验
            String msg = String.format("sign=%s", sign);
            throw new ValidationException(ErrorType.SIGN_ERROR, msg);
        }
    }

    /**
     * 返回签名，并封装request
     *
     * @param request
     * @param key 密钥
     * @param <T>
     * @return
     */
    public static <T> String signature(Request<T> request, String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("key is null");
        }
        if (request.getVender() == null ) {
            throw new NullPointerException("vender is null");
        }
        if (StringUtils.isBlank(request.getPlatform())) {
            throw new NullPointerException("platform is null");
        }
        if (StringUtils.isBlank(request.getDeviceId())) {
            throw new NullPointerException("device_id is null");
        }
        if (StringUtils.isBlank(request.getVersion())) {
            throw new NullPointerException("version is null");
        }
        if (request.getTimestamp() == null) {
            throw new NullPointerException("timestamp is null");
        }
        if (request.getRequestBody() == null && StringUtils.isBlank(gson.toJson(request.getRequestBody()))) {
            throw new NullPointerException("content is null");
        }
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
        treeMap.put("vender", request.getVender());
        treeMap.put("platform", request.getPlatform());
        treeMap.put("device_id", request.getDeviceId());
        treeMap.put("version", request.getVersion());
        treeMap.put("timestamp", request.getTimestamp());
        treeMap.put("content", gson.toJson(request.getRequestBody()));
        String serverSign = String.format("%s&key=%s", treeMap2ascString(treeMap), key);
        String sign = EncryptUtils.md5(serverSign).toLowerCase();
        request.setSign(sign);
        return sign;
    }

    /**
     * 获取实体
     *
     * @param <T>
     * @param clazz
     * @return
     * @author youngtan99@163.com
     * @2015年1月26日
     */
    public static <T> T getRequestBody(Request<T> request, Class<T> clazz) {
        return gson.fromJson(gson.toJson(request.getRequestBody()), clazz);
    }

    /**
     * 将TreeMap中所有参数按升序转换成字符串，格式：key1=value1&key2=value2
     *
     * @param map
     * @return
     */
    private static String treeMap2ascString(TreeMap<String, Object> map) {
        return treeMap2ascString(map, null);
    }

    /**
     * 将TreeMap中所有参数按升序转换成字符串，格式：key1=value1&key2=value2
     *
     * @param map
     * @param charset 编码，为空则不编码
     * @return
     */
    private static String treeMap2ascString(TreeMap<String, Object> map, String charset) {
        return treeMap2ascString(map, charset, '&');
    }

    /**
     * 将TreeMap中所有参数按升序转换成字符串
     *
     * @param map
     * @param charset 编码，为空则不编码
     * @param link    连接符
     * @return
     */
    private static String treeMap2ascString(TreeMap<String, Object> map, String charset, Character link) {
        StringBuilder sb = new StringBuilder();
        try {
            Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, Object> entry = iter.next();
                sb.append(entry.getKey()
                        + "="
                        + (StringUtils.isNotBlank(charset) ? URLEncoder.encode(entry.getValue().toString(), charset)
                        : entry.getValue()) + (link != null ? link : ""));
            }
            if (link != null) {
                sb = sb.length() > 0 && sb.charAt(sb.length() - 1) == link ? sb.deleteCharAt(sb.length() - 1) : sb;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
