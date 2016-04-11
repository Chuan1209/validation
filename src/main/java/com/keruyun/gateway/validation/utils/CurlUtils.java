package com.keruyun.gateway.validation.utils;

/**
 * curl命令帮助类
 * 
 * @author tany@shishike.com
 * @since 2015年4月7日
 */
public class CurlUtils {
    
    /**
     * 返回json的curl命令
     * 
     * @author tany@shishike.com
     * @2015年4月7日
     * @param httpMethod
     * @param url
     * @param args
     * @return
     */
    public static String jsonCurl(String httpMethod, String url, String args) {
        return curl(httpMethod, "application/json", "Application/json", url, args);
    }

    /**
     * 返回json的curl命令
     * @param httpMethod
     * @param contentType
     * @param accept
     * @param url
     * @param args
     * @return
     */
    private static String curl(String httpMethod, String contentType, String accept, String url, String args) {
        StringBuffer curlString = new StringBuffer();
        curlString.append("curl ");
        curlString.append(" -X ").append(httpMethod);
        curlString.append(" -H \"Accept: ").append(accept).append("\"");
        curlString.append(" -H \"Content-Type: ").append(contentType).append("\"");
        curlString.append(" ").append(url).append(" ");
        curlString.append(" -d '").append(args).append("'");
        return curlString.toString();
    }
}
