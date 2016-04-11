package com.keruyun.gateway.validation.response;

import com.google.gson.annotations.SerializedName;
import com.keruyun.gateway.validation.type.ErrorType;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.UUID;

/**
 * 返回参数封装，可继承扩展
 *
 * @author tany@shishike.com
 * @since 2015年1月23日
 */
public class Response<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -91901620031158368L;

    public Response() {
        super();
    }

    public Response(ErrorType errorType) {
        this.returnCode = errorType.getKey();
        this.returnMessage = errorType.getValue();
    }

    public Response(int returnCode, String returnMessage, T responseBody) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.responseBody = responseBody;
    }


    /**
     * @return the responseBody
     */
    public T getResponseBody() {
        return responseBody;
    }

    /**
     * @param responseBody the responseBody to set
     */
    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }

    public void setResponseMessage(ErrorType errorType, String msg) {
        this.returnCode = errorType.getKey();
        if (StringUtils.isNotBlank(msg)) {
            this.setReturnMessage(String.format("%s[%s]", errorType.getValue(), msg));
        } else {
            this.setReturnMessage(String.format("%s", errorType.getValue()));
        }
    }

    /**
     * 类型不一样的时候用此构造函数
     * @param code
     * @param message
     */
    public Response(int code,String message) {
        this.returnCode = code;
        this.returnMessage = message;
    }
    @SerializedName("result")
    protected T responseBody;


    @SerializedName("code")
    protected int returnCode; // 返回编码
    @SerializedName("message")
    protected String returnMessage; // 返回信息
    @SerializedName("message_uuid")
    protected String responseUUID = UUID.randomUUID().toString(); //错误信息序列码

    public String getResponseUUID() {
        return responseUUID;
    }

    public void setResponseUUID(String responseUUID) {
        this.responseUUID = responseUUID;
    }

    /**
     * @return the returnCode
     */
    public int getReturnCode() {
        return returnCode;
    }

    /**
     * @param returnCode the returnCode to set
     */
    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    /**
     * @return the returnMessage
     */
    public String getReturnMessage() {
        return returnMessage;
    }

    /**
     * @param returnMessage the returnMessage to set
     */
    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    private Pager pager;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
