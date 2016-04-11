package com.keruyun.gateway.validation.service;

import com.google.gson.annotations.SerializedName;
import com.keruyun.gateway.validation.annotation.ValidateProperty;
import com.keruyun.gateway.validation.type.RegexType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tany@shishike.com on 15/12/7.
 */
public class User implements Serializable {

//    @SerializedName("student")
//    @ValidateProperty(clazz = Name.class, regexType = RegexType.OBJECT)
//    private Name student;
    @ValidateProperty(clazz = Name.class, regexType = RegexType.ARRAY)
    @SerializedName("student_list")
    private List<Name> studentList;
    @ValidateProperty(clazz = GenderType.class, regexType = RegexType.ENUM)
    @SerializedName("gender")
    private Integer gender;
    @ValidateProperty(regexType = RegexType.CHARACTER, maxLength = 10, minLength = 1)
    @SerializedName("name")
    private String name;

//    public Name getStudent() {
//        return student;
//    }
//
//    public void setStudent(Name student) {
//        this.student = student;
//    }

    public List<Name> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Name> studentList) {
        this.studentList = studentList;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
