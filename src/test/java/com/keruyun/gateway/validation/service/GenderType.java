package com.keruyun.gateway.validation.service;

public enum GenderType{

    FEMALE(0, "女"),
    MALE(1, "男"),
    UNKNOWN(-1, "未知");


    private int key;
    private String value;

    private GenderType(int key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 根据key获取对应的描述
     *
     * @param key
     * @return
     */
    public static String getGender(int key) {
        for (GenderType gender : values()) {
            if (gender.key == key) {
                return gender.value;
            }
        }
        return UNKNOWN.value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}