package com.keruyun.gateway.validation.type;

/**
 * 异常枚举类型<p>
 * 说明：如果出现的异常是业务异常，可以在BUSINESS_ERROR(2000, "业务异常")，
 * 下面添加其他异常，但是必须保证error的key为2001到3000之间的数值，保证是业务异常的子数据。<p>
 * 例如:RDER_NOT_EXISTS(2003,  "订单不存在")，异常详细信息可为：查询异常[查询商户信息异常]
 * 
 * @author tany@shishike.com
 *
 */
public enum ErrorType {
	OK(0, "成功[OK]"),

	PARAM_DATA_ERROR(1000, "入参校验异常"),
	SIGN_ERROR(1001, "签名校验异常"),
	BUSINESS_ERROR(2000, "业务异常"),
	VENDER_ERROR(2001,  "合作者标识错误"),
    ORDER_NULL(2002,  "订单ID不能为空"),
    ORDER_NOT_EXISTS(2003,  "订单不存在"),
    ORDER_COMPLETE(2004,  "订单已经完成"),
    ORDER_CANCELED(2005,  "订单已经被取消"),
    ORDER_REJECTED(2006,  "订单已经被拒绝"),
    ORDER_INVALID(2007,  "订单已经被作废"),
    ORDER_NOT_REVERSE(2008,  "订单状态不能被逆转"),
    ORDER_NOW(2009,  "订单状态已经为当前值"),
    ORDER_DUPLICATED(2010,  "重复的订单"),
    ORDER_PRICE_ERROR(2011,  "价格计算错误"),
    ORDER_SOURCE_ERROR(2012,  "订单来源错误"),
	ORDER_CANCEL_ERROR_BY_ENTRANCE(2013,  "订单已入场，取消失败"),
	ORDER_CANCEL_ERROR_BY_INVALIDE(2014,  "订单过号或作废，取消失败"),

    SHOP_NOT_EXISTS(2100,  "商户信息不存在"),
    DISH_NOT_EXISTS(2101,  "商户菜品不存在"),
    SHOP_CAN_NOT_ORDER(2102,  "商户不接单"),
    SHOP_CLOSED(2103,  "商户已打烊"),
    SHOP_NOT_SUPPORT(2104,  "商户不支持接收第三方订单"),
    SHOP_NOT_REPORT(2105,  "商户信息还未分发给第三方"),
	SHOP_DEVICE_NOT_ONLINE(2106,  "商家离线( 设备不在线)"),


    USER_SOURCE_ERROR(2200,  "用户来源错误"),
	SYSTEM_ERROR(3000, "系统异常"),
	INTERNAL_INTERFACE_ERROR(3001,  "内部接口异常"),
	UNKNOW_ERROR(-1,    "未知异常[UNKNOWN ERROR]");
	
	
	

	private int key;
	private String value;

	/**
	 * @return the key
	 */
	public int getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	private ErrorType(int key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * 获取异常类型
	 * 
	 * @author tany@shishike.com
	 * @2015年1月26日
	 * @param key
	 * @return
	 */
	public static ErrorType getResponseErrorType(int key) {
		ErrorType[] errors = ErrorType.values();
		for (ErrorType error : errors) {
			if (error.getKey() == key) {
				return error;
			}
		}
		return UNKNOW_ERROR;
	}
}