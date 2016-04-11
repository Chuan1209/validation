package com.keruyun.gateway.validation.service;

import com.google.gson.annotations.SerializedName;
import com.keruyun.gateway.validation.annotation.ValidateProperty;
import com.keruyun.gateway.validation.type.RegexType;

import java.io.Serializable;
import java.util.List;

public class Name implements Serializable {

    @ValidateProperty(regexType = RegexType.CHARACTER, maxLength = 10, minLength = 1)
    @SerializedName("user_name")
    private String username;

    @ValidateProperty(nullable = true,clazz = Tec.class, regexType = RegexType.ARRAY)
    @SerializedName("tec_list")
    private List<Tec> tecList;
    public String getUsername() {
        return username;
    }

    public List<Tec> getTecList() {
        return tecList;
    }

    public void setTecList(List<Tec> tecList) {
        this.tecList = tecList;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}