package com.keruyun.gateway.validation.service;


import com.google.gson.annotations.SerializedName;
import com.keruyun.gateway.validation.annotation.ValidateProperty;
import com.keruyun.gateway.validation.exception.ValidationException;
import com.keruyun.gateway.validation.type.ErrorType;
import com.keruyun.gateway.validation.type.RegexType;
import com.keruyun.gateway.validation.utils.RegexUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 校验服务处理类
 *
 * @author tany@shishike.com
 * @since 2015年2月27日
 */
public class ValidationService {

    public ValidationService() {
        super();
    }

    private final static Logger logger = LoggerFactory.getLogger(ValidationService.class);

    private final static String ENUM_KEY = "key";

    /**
     * 公用校验方法
     *
     * @param object
     * @throws
     * @author tany@shishike.com
     * @2015年2月27日
     */
    public static void valid(Object object, String des, List<String> errors) throws ValidationException {
        // 获取object的类型
        Class<? extends Object> clazz = object.getClass();
        if (StringUtils.isBlank(des)) {
            des = convert(clazz.getSimpleName());
        }

        Field[] fields = getFields(clazz);
        if(fields == null){
            return;
        }
        // 获取该类型声明的成员
        // 遍历属性
        for (Field field : fields) {
            try {
                if (field == null || field.getName().equals("serialVersionUID")) {
                    continue;
                }
                // 对于private私有化的成员变量，通过setAccessible来修改器访问权限
                field.setAccessible(true);
                validate(field, object, des, errors);
                // 重新设置会私有权限
                field.setAccessible(false);
            } catch (ValidationException e) {
                logger.error(" Validation Service Error ==>  field : {} , error : {} ",
                        clazz.getName() + "." + field.getName(),
                        e.getMessage());
                throw e;
            }
        }
    }


    /**
     * 校验处理
     *
     * @param field
     * @param object
     * @throws ValidationException
     * @author tany@shishike.com
     * @2015年2月27日
     */

    private static void validate(Field field, Object object, String des, List<String> errors) throws ValidationException {
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalArgumentException e) {
            logger.error("校验服务异常", e);
            throw new ValidationException(ErrorType.SYSTEM_ERROR, "校验服务异常");
        } catch (IllegalAccessException e) {
            logger.error("校验服务异常", e);
            throw new ValidationException(ErrorType.SYSTEM_ERROR, "校验服务异常");
        }

        // 获取注解
        ValidateProperty validateProperty = field.getAnnotation(ValidateProperty.class);
        if (validateProperty == null) {
            return;
        }
        SerializedName jsonProperty = field.getAnnotation(SerializedName.class);
        String fieldName = field.getName();
        if (jsonProperty != null) {
            fieldName = jsonProperty.value();
        }
        if (des.lastIndexOf(".") > 0) {
            des = des + fieldName;
        } else {
            des = des + "." + fieldName;
        }
        // 校验正则表达式
        String regexExpression = validateProperty.regexExpression();
        // 是否为空
        if (!validateProperty.nullable()) {
            if (value == null || StringUtils.isBlank(value.toString())) {
                errors.add(des + "为空");
                return;
            }
        }
        if (value != null && StringUtils.isNotBlank(value.toString())) {
            int len = validateProperty.length();// 定长
            int max = validateProperty.maxLength();// 获取最大值
            int min = validateProperty.minLength();// 获取最小值
            int length = value.toString().length(); // 字段长度
            if (len != 0) {
                if (length != len) {
                    errors.add(des + "长度为" + len + "位长度的定值,value : " + value.toString() + ",length : " + length);
                    return;
                }
            } else {
                if (max != 0 && length > max) {
                    errors.add(des + "长度不能大于" + max + ",value : " + value.toString() + ",length : " + length);
                    return;
                }

                if (min != 0 && length < min) {
                    errors.add(des + "长度不能小于" + min + ",value : " + value.toString() + ",length : " + length);
                    return;
                }
            }
            if (des.equals("request.content") && fieldName.equals("content")) {
                valid(value, des + ".", errors);
            }
            if (validateProperty.regexType() != RegexType.NONE) {
                switch (validateProperty.regexType()) {
                    case BOOL:
                        if (!(value.toString().equals("true") || value.toString().equals("false"))) {
                            errors.add(des + "必须为0或者1,value : " + value.toString());
                            return;
                        }
                    case CHARACTER:// 字符
                        if (StringUtils.isNotBlank(regexExpression)
                                && !RegexUtils.validByRegex(value.toString(), regexExpression)) {
                            errors.add(des + "格式错误，校验规则 " + regexExpression + ",value : " + value.toString());
                            return;
                        }
                        break;
                    case INT: // 整数
                        if (!RegexUtils.isInteger(value.toString(), regexExpression)) {
                            errors.add(des + "请输入正确格式整型数字,value : " + value.toString());
                            return;
                        }
                        break;

                    case LONG: // 长整型
                        if (!RegexUtils.isLong(value.toString(), regexExpression)) {
                            errors.add(des + "请输入正确格式长整型数字,value : " + value.toString());
                            return;
                        }
                        break;
                    case DECIMAL: // 浮点型
                        if (!RegexUtils.isDecimal(value.toString(), regexExpression)) {
                            errors.add(des + "请输入正确格式的浮点型数字,value : " + value.toString());
                            return;
                        }
                        break;
                    case EMAIL: // 电子邮件
                        if (!RegexUtils.isEmail(value.toString())) {
                            errors.add(des + "格式错误(应为Email格式),value : " + value.toString());
                            return;
                        }
                        break;
                    case IP: // ip地址
                        if (!RegexUtils.isIP(value.toString())) {
                            errors.add(des + "格式错误(应为ip地址格式),value : " + value.toString());
                            return;
                        }
                        break;
                    case URL: // 网站地址
                        if (!RegexUtils.isURL(value.toString())) {
                            errors.add(des + "格式错误(应为网站地址格式),value : " + value.toString());
                            return;
                        }
                        break;
                    case PHONENUMBER: // 电话号码
                        if (!RegexUtils.isPhoneNumber(value.toString(), regexExpression)) {
                            errors.add(des + "格式错误(应为电话、手机号码格式),value : " + value.toString());
                            return;
                        }
                        break;
                    case DATE: // 日期
                        if (StringUtils.isBlank(regexExpression)) {
                            regexExpression = RegexUtils.YYYY_MM_DD_HH_MM_SS;
                        }
                        if (!RegexUtils.isDate(value.toString(), regexExpression)) {
                            errors.add(des + "格式错误,value : " + value.toString());
                            return;
                        }
                        break;
                    case ARRAY:
                        try {
                            List list = (List) value;
                            for (int i = 0; i < list.size(); i++) {
                                valid(list.get(i), des + "[" + i + "].", errors);
                            }
                        } catch (ValidationException e) {
                            throw e;
                        }
                        break;
                    case MAP:
                        break;
                    case OBJECT:
                        try {
                            if (StringUtils.isNotBlank(des)) {
                                des = des + ".";
                            }
                            valid(value, des, errors);
                        } catch (ValidationException e) {
                            throw e;
                        }
                        break;
                    case ENUM:// 枚举类型校验
                        try {
                            validateEnum(value.toString(), des, validateProperty.clazz());
                        } catch (ValidationException e) {
                            errors.add(e.getMessage());
                            return;
                        }
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 验证枚举类型
     *
     * @param clazz
     * @return
     */
    public static void validateEnum(String filedValue, String des, Class clazz) throws ValidationException {
        if (!clazz.isEnum()) {// clazz 是否为枚举类型
            throw new ValidationException(ErrorType.PARAM_DATA_ERROR, des + "请输入正确的枚举类型");
        }
        List<String> enumKeyValues = new ArrayList<String>();
        try {
            Object[] objects = clazz.getEnumConstants();
            for (Object object : objects) {
                // 获取枚举类型的values方法
                Method m = object.getClass().getDeclaredMethod("values", null);
                // 执行values方法获取返回的枚举类型
                Object[] result = (Object[]) m.invoke(object, null);
                List list = Arrays.asList(result);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    Object objOne = it.next();
                    Field key = objOne.getClass().getDeclaredField(ENUM_KEY);
                    key.setAccessible(true);
                    enumKeyValues.add(key.get(objOne).toString());
                    key.setAccessible(false);
                }
                break;
            }

        } catch (Exception e) {
            logger.error("调用验证服务校验枚举类型异常", e);
            throw new ValidationException(ErrorType.PARAM_DATA_ERROR, des + "校验调用验证服务验证枚举类型异常");
        }
        if (!enumKeyValues.contains(filedValue)) {
            throw new ValidationException(ErrorType.PARAM_DATA_ERROR, des + "格式错误," +
                    "value = " + filedValue.toString() + ",请输入" + enumKeyValues.toString());
        }
    }


    /**
     * 转换为下划线
     *
     * @param des
     * @return
     */
    private static String convert(String des) {
        des = des.length() > 1 ? (des.substring(0, 1) + des.substring(1).replaceAll("([A-Z])", "_$1")) : des;
        if(des.indexOf("_t_o") > 0 || des.indexOf("_v_o") > 0 ){
            des.replace("_t_o","_to");
            des.replace("_v_o","_vo");
        }
        return des.toLowerCase();
    }

    private static Field[] getFields(Class<? extends Object> clazz) {
        // 如果是Request，获取Request的父类
        Class<? extends Object> superClazz = clazz.getSuperclass();
        Field[] fileds = null;
        Field[] thisFields = clazz.getDeclaredFields();
        if (!superClazz.getName().contains("java.lang.Object")) {
            Field[] supperFields = clazz.getSuperclass().getDeclaredFields();
            fileds = new Field[thisFields.length + supperFields.length];
            System.arraycopy(thisFields, 0, fileds, 0, thisFields.length);
            System.arraycopy(supperFields, 0, fileds, thisFields.length, supperFields.length);
            fileds = thisFields;
        }
        return fileds;
    }

}
