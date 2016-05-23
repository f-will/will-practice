package com.will.framework.util.tool;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * url参数解析工具类
 * @ClassName UrlParamUtil
 * @author will
 */
public class UrlParamUtil {
	
	/**
	 * 获取Url中某参数的值//获取Url中某参数的值
	 * @Title:getUrlParamVal
	 * @Description: TODO
	 * @param url URL
	 * @param name 参数名
	 * @return String 参数值 
	 */	
    public static String getUrlParamVal(String url,String name) {
		Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.clear();
        if (!StringUtils.isEmpty(url)) {// 如果URL不是空字符串
            if(url.indexOf('?')>-1){
            	 String paramStr = url.substring(url.indexOf('?') + 1,url.length());
                 String paramaters[] = StringUtils.isEmpty(paramStr)?null:paramStr.split("&");
                 for (String param : paramaters) {
                     String values[] = param.split("=");
                     paramMap.put(values[0], values[1]);
                 }
            }
           
        }
        return paramMap.get(name);
    }
    /**
     * 在url中增加参数
     * @Title:addUrlParamVal
     * @Description: TODO
     * @param url
     * @param paramName
     * @param paramVal
     * @return String 返回类型 {@link String} 
     */
    public static String addUrlParamVal(String url,String paramName,String paramVal){
		if (!StringUtils.isEmpty(url)) {// 如果URL不是空字符串
			
			if (url.indexOf(paramName + "=") > -1) {
				//修改参数值
		        url = url.replaceAll("(" + paramName +"=[^&]*)", paramName + "=" + paramVal); 		      

			} else {
				//增加参数值
				if (url.indexOf('?') > -1) {
					url += "&" + paramName + "=" + paramVal;
				} else {
					url += "?" + paramName + "=" + paramVal;
				}
			}

		}
    	return url;
    };
    

}
