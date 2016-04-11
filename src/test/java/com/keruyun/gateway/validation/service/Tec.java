package com.keruyun.gateway.validation.service;

import com.google.gson.annotations.SerializedName;
import com.keruyun.gateway.validation.annotation.ValidateProperty;
import com.keruyun.gateway.validation.type.RegexType;

import java.io.Serializable;

/**
 * Created by tany@shishike.com on 15/12/7.
 */
public class Tec implements Serializable {
    @ValidateProperty(regexType = RegexType.CHARACTER, maxLength = 10, minLength = 1)
    @SerializedName("user_name")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
