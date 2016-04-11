package com.keruyun.gateway.validation.bean;


import com.google.gson.annotations.SerializedName;
import com.keruyun.gateway.validation.annotation.ValidateProperty;
import com.keruyun.gateway.validation.type.OperatorsType;
import com.keruyun.gateway.validation.type.RegexType;

import java.io.Serializable;

/**
 * SQL 操作符键值对
 */
public class KeyValue implements Serializable{
	@ValidateProperty(regexType = RegexType.CHARACTER)
	@SerializedName("key")
	private String key;//key

	//@Validation(regexType = RegexType.ENUM,clazz = OperatorsType.class)
	@ValidateProperty(regexType = RegexType.OBJECT,nullable = true)
	@SerializedName("value")
	private Object value;//value

	@ValidateProperty(regexType = RegexType.ENUM,clazz = OperatorsType.class)
	@SerializedName("operator")
	private String operator;//操作符  >= <= > < = like in desc asc
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public KeyValue() {
	}

	public KeyValue(String key, Object value, String operator) {
		this.key = key;
		this.value = value;
		this.operator = operator;
	}
}
