package com.will.framework.util.json;

import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
/**
 * Json工具类
 * 这里只提供常用工具类，如果有特殊使用需求，
 * 请直接使用{@link com.alibaba.fastjson.JSON} API进行操作
 * @author will
 *
 */
public class JsonUtil {
	/**
	 * 将对象转换为Json字符串
	 * 按照默认日期格式转换
	 * @param object 需要转换的对象
	 * @return Json字符串
	 */
	public static String toJSONString(Object object){
		return JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 将对象转换为Json字符串
	 * 可以指定自定义的日期格式
	 * @param object 需要转换的对象
	 * @param dateFormat 日期格式，比如：yyyy-MM-dd HH:mm:ss 
	 * @return Json字符串
	 */
	public static String toJSONString(Object object,String dateFormat){
		return JSON.toJSONStringWithDateFormat(object,dateFormat, SerializerFeature.WriteDateUseDateFormat);
	}
	/**
	 * 解析Json字符串为对象
	 * @param jsonString Json字符串
	 * @param clazz 解析后的对象Class
	 * @return 指定Class的对象
	 */
	public static <T> T parseObject(String jsonString,Class<T> clazz){
		return JSON.parseObject(jsonString, clazz);
	}
	/**
	 * 解析Json字符串为List
	 * @param jsonString Json字符串
	 * @param clazz List元素的类型Class
	 * @return 指定Class元素的List集合
	 */
	public static <T> List<T> parseArray(String jsonString,Class<T> clazz){
		return JSON.parseArray(jsonString, clazz);
	}
	
	private JsonUtil() {}
	
}
