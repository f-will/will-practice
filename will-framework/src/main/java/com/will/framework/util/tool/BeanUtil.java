package com.will.framework.util.tool;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
/**
 * Java bean 工具类
 * 可以忽略指定基本类型和包装类型之间的复制
 * 忽略空值复制
 * @author will
 *
 */
public class BeanUtil extends org.springframework.beans.BeanUtils{
	//基本类型和包装类型映射Map
	private static Map<String,Integer> typeMap=new HashMap<String,Integer>();
	
	private BeanUtil() {
		// TODO Auto-generated constructor stub
	}
	private static boolean isMatch(Class<?> a,Class<?> b){
		if(a.isAssignableFrom(b)){
			return true;
		}
		Integer value1=typeMap.get(a.getSimpleName());
		if(null!=value1){
			Integer value2=typeMap.get(b.getSimpleName());
			return (null!=value2 && value1==value2);
		}
		return false;
	}
	/**
	 * Copy the property values of the given source bean into the given target bean.
	 * <p>Note: The source and target classes do not have to match or even be derived
	 * from each other, as long as the properties match. Any bean properties that the
	 * source bean exposes but the target bean does not will silently be ignored.
	 * @param source the source bean
	 * @param target the target bean
	 * @throws BeansException if the copying failed
	 * @see BeanWrapper
	 */
	public static void copyProperties(Object source, Object target) throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null &&
							(isMatch(writeMethod.getParameterTypes()[0],readMethod.getReturnType()))) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							//如果为空不复制
							if(null!=value){
								if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(target, value);
							}
						}
						catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	}
	static{
		initTypeMap();
	}
	private static void initTypeMap(){
		typeMap.put("Integer", 1);
		typeMap.put("int", 1);
		
		typeMap.put("Long", 2);
		typeMap.put("long", 2);
		
		typeMap.put("Float", 3);
		typeMap.put("float", 3);
		
		typeMap.put("Double", 4);
		typeMap.put("double", 4);

		typeMap.put("Boolean", 5);
		typeMap.put("boolean", 5);
	}
}
