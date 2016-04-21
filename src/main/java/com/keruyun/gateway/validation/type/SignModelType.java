package com.keruyun.gateway.validation.type;

/**
 * Created by youngtan99@163.com on 15/10/23.
 */
public enum SignModelType {
    RUN("RUN"),DEBUG("DEBUG");

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    SignModelType(String key) {
        this.key = key;
    }

    public static SignModelType getType(String key){
        SignModelType[] types = SignModelType.values();
        for (SignModelType type : types) {
            if(type.getKey().equals(key)){
                return type;
            }
        }
        return RUN;
    }
}
