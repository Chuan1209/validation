package com.keruyun.gateway.validation.request;

import com.google.gson.annotations.SerializedName;
import com.keruyun.gateway.validation.type.SignModelType;
import com.keruyun.gateway.validation.annotation.ValidateProperty;
import com.keruyun.gateway.validation.type.RegexType;
import com.keruyun.gateway.validation.utils.RegexUtils;

import java.io.Serializable;

/**
 * 请求参数封装
 *
 * @param <T>
 * @author tany@shishike.com
 * @since 2015年1月23日
 */
public class Request<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1990559659670684491L;


    public Request() {
        super();
    }


    /**
     *
     */
    @ValidateProperty(length = 5, regexType = RegexType.INT)
    protected Integer vender; // 合作方唯一标识

    @ValidateProperty(maxLength = 100, minLength = 1, regexType = RegexType.CHARACTER)
    protected String platform; // 合作方平台

    @SerializedName("device_id")
    @ValidateProperty(maxLength = 100, minLength = 1, regexType = RegexType.CHARACTER)
    protected String deviceId; // 设备标识

    @ValidateProperty(maxLength = 20, minLength = 1, regexType = RegexType.CHARACTER, regexExpression = RegexUtils.VERSION_REGEX)
    protected String version; // 合作方版本号

    @ValidateProperty(length = 10, regexType = RegexType.LONG)
    protected Long timestamp;// 时间戳

    @ValidateProperty(length = 32, regexType = RegexType.CHARACTER, regexExpression = RegexUtils.STR_LETTER_NUM_REGEX)
    protected String sign; // 加密签名

    @SerializedName("sign_model")
    @ValidateProperty(nullable = true, regexType = RegexType.ENUM, clazz = SignModelType.class)
    protected String signModel = SignModelType.RUN.name(); // 签名模式

    public String getSignModel() {
        return signModel;
    }

    public void setSignModel(String signModel) {
        this.signModel = signModel;
    }

    /**
     * 签名密钥
     */
    private  String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 请求参数
     */
    @ValidateProperty(nullable = false)
    @SerializedName("content")
    protected T requestBody;

    /**
     * @return the platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * @param platform the platform to set
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * @return the vender
     */
    public Integer getVender() {
        return vender;
    }

    /**
     * @param vender the vender to set
     */
    public void setVender(Integer vender) {
        this.vender = vender;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign the sign to set
     */
    public void setSign(String sign) {
        this.sign = sign;
    }


    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public void setRequestBody(T requestBody) {
        this.requestBody = requestBody;
    }

    public T getRequestBody() {
        return requestBody;
    }
}
