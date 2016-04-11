package com.keruyun.gateway.validation.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 校验帮助类
 *
 * @author tany@shishike.com
 * @since 2015年2月27日
 */
public class RegexUtils {

    /**
     * 非负整数正则表达式
     */
    public static final String NONNEGATIVE_NO_ZERO_INT_REGEX = "^[1-9]\\d*$";

    /**
     * 非负整数正则表达式(包括0)
     */
    public static final String NONNEGATIVE_INT_REGEX = "^[1-9]\\d*|0$";
    /**
     * 负整数正则表达式(包括0)
     */
    public static final String NEGATIVE_INT_REGEX = "^-[1-9]\\d*|0$";
    /**
     * 整数正则表达式
     */
    public static final String INT_REGEX = "^-?[1-9]\\d*$";
    /**
     * 非负浮点数正则表达式(包括0)
     */
    public static final String NONNEGATIVE_FLOAT_REGEX = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$";
    /**
     * 负浮点数正则表达式(包括0)
     */
    public static final String ONEGATIVE_FLOAT_REGEX = "^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$";
    /**
     * 浮点数正则表达式(包括0)
     */
    public static final String FLOAT_REGEX = "^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$";
    /**
     * 邮箱正则表达式
     */
    public static final String EMAIL_REGEX = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
    /**
     * ip地址正则表达式
     */
    public static final String IPADDER_REGEX = "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";
    /**
     * URL地址正则表达式
     */
    public static final String URL_REGEX = "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$";

    public static final String WEB_SITE_REGEX = "^((https|http|ftp|rtsp|mms)?://)"
            + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
            + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
            + "|" // 允许IP和DOMAIN（域名）
            + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
            + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
            + "[a-z]{2,6})" // first level domain- .com or .museum
            + "(:[0-9]{1,5})?" // 端口- :80
            + "((/?)|" // a slash isn't required if there is no file name
            + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    /**
     * 手机号码正则表达式
     */
    public static final String MOBILE_NO_REGEX = "^((13[0-9])|(14[0-9])|(17[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
    /**
     * 是否包含中文正则表达式
     */
    public static final String CHINESE_REGEX = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";
    /**
     * 是否电话号码正则表达式
     */
    public static final String TEL_NO_REGEX = "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$";
    /**
     * 版本号格式正则表达式
     */
    public static final String VERSION_REGEX = "^\\d*(\\.?\\d+)*$";

    /**
     * 是否为数字字母正则表达式
     */
    public static final String STR_LETTER_NUM_REGEX = "^[a-zA-Z0-9]+$";

    /**
     * yyyy-MM-dd HH:mm:ss 日期格式
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd 日期格式
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * yyyyMMddhhmmss 日期格式
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddhhmmss";

    /**
     * yyyyMMdd 日期格式
     */
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 是否数字
     *
     * @param s               字符串
     * @param regexExpression 正则表单
     * @return
     * @author tany@shishike.com
     * @2015年2月27日
     */
    public static boolean isDecimal(String s, String regexExpression) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        // 表达式不为空，则根据正则校验
        if (StringUtils.isNotBlank(regexExpression)) {
            return s.matches(regexExpression);
        }
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 电话号码校验
     *
     * @param s
     * @param regexExpression
     * @return
     * @author tany@shishike.com
     * @2015年2月27日
     */
    public static boolean isPhoneNumber(String s, String regexExpression) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        if (StringUtils.isNotBlank(regexExpression)) {
            return isPhoneNO(s, regexExpression);
        }
        return isPhoneNumber(s);
    }

    /**
     * 时间格式校验
     *
     * @param s
     * @param regexExpression
     * @return
     * @author tany@shishike.com
     * @2015年2月27日
     */
    public static boolean isDate(String s, String regexExpression) {
        boolean flag = false;
        if (StringUtils.isBlank(s)) {
            return false;
        }
        if (StringUtils.isNotBlank(regexExpression)) {
            SimpleDateFormat sf = new SimpleDateFormat(regexExpression);
            try {
                sf.parse(s);
                flag = true;
            } catch (ParseException e) {
                flag = false;
            }
        }
        // ISO 8601时间转换
        if (!flag) {
            try {
                toFormatDate(s, regexExpression);
                flag = true;
            } catch (ParseException e) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 邮箱校验
     *
     * @param s
     * @return
     * @author tany@shishike.com
     * @2015年2月27日
     */
    public static boolean isEmail(String s) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        return s.matches(EMAIL_REGEX);
    }

    /**
     * ip地址校验
     *
     * @param s
     * @return
     * @author tany@shishike.com
     * @2015年2月27日
     */
    public static boolean isIP(String s) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        return s.matches(IPADDER_REGEX);
    }

    /**
     * URL地址校验
     *
     * @param s
     * @return
     * @author tany@shishike.com
     * @2015年2月27日
     */
    public static boolean isURL(String s) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        return s.matches(URL_REGEX);
    }

    public static boolean isPhoneNumber(String s) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        return isPhoneNO(s, MOBILE_NO_REGEX);
    }

    /**
     * 整型校验
     *
     * @param s
     * @param regexExpression
     * @return
     * @author tany@shishike.com
     * @2015年2月27日
     */
    public static boolean isInteger(String s, String regexExpression) {
        if (StringUtils.isBlank(s)) {
            return false;
        }


        if (StringUtils.isNotBlank(regexExpression)) {
            return s.matches(regexExpression);
        }
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 长整型校验
     *
     * @param s
     * @param regexExpression
     * @return
     * @author tany@shishike.com
     * @2015年2月27日
     */
    public static boolean isLong(String s, String regexExpression) {
        if (StringUtils.isBlank(s)) {
            return false;
        }
        if (StringUtils.isNotBlank(regexExpression)) {
            return s.matches(regexExpression);
        }
        try {
            Long.parseLong(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证手机号码
     *
     * @param s
     * @param regexExpression
     * @return [0-9]{5,9}
     */
    public static boolean isPhoneNO(String s, String regexExpression) {
        boolean flag = false;
        try {
            flag = s.matches(regexExpression);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 根据正则表达式校验
     *
     * @param s
     * @param regexExpression
     * @return
     * @author tany@shishike.com
     * @2015年2月27日
     */
    public static boolean validByRegex(String s, String regexExpression) {
        return s.matches(regexExpression);
    }


    /**
     * Transform Calendar to ISO 8601 string.
     */
    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /**
     * Get current date and time formatted as ISO 8601 string.
     */
    public static String now() {
        return fromCalendar(GregorianCalendar.getInstance());
    }

    /**
     * Transform ISO 8601 string to Calendar.
     */
    public static Calendar toCalendar(final String iso8601string) throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.replace("Z", "+00:00");
        try {
            s = s.substring(0, 22) + s.substring(23); // to get rid of the ":"
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid length", 0);
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(s);
        calendar.setTime(date);
        return calendar;
    }

    public static String toFormatDate(final String iso8601string, String format) throws ParseException {
        if (StringUtils.isBlank(format)) {
            format = YYYY_MM_DD_HH_MM_SS;
        }
        return new SimpleDateFormat(format).format(toCalendar(iso8601string).getTime());
    }

    public static void main(String[] args) {
        String date = "2015-03-24T09:47:25+00:00";
        String s = "028-88888888";
        isPhoneNumber(s);
        try {
            System.out.println(toFormatDate(date, null));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
