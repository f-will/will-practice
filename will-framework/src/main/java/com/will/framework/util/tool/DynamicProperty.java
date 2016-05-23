package com.will.framework.util.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DynamicProperty
 * @Description: 动态属性类
 * @author will
 * @date 2014年11月10日 下午4:33:24
 * 
 */
public class DynamicProperty {
	/**
	 * @Title: newInstance
	 * @Description: 动态创建一个类
	 * @param @param className
	 * @param @return 类
	 * @return Object 返回类型
	 * @throws
	 */
	public static Object newInstance(String className) {
		if ("".equals(className))
			return null;

		try {
			return Class.forName(className).newInstance();
		} catch (Exception ex) {
			// ex.printStackTrace();
			return null;
		}
	}

	/**
	 * @Title: getAllProperties
	 * @Description: 返回一个对象的全部属性，支持继承的类的父类的属性
	 * @param @param target
	 * @param @return 设定文件
	 * @return List<String> 返回类型
	 * @throws
	 */
	public static List<String> getAllProperties(Object target) {
		List<String> retList = new ArrayList<String>();
		Class<?> objClass = target.getClass();
		while (objClass != null && !objClass.getSimpleName().equals("Class")
				&& !objClass.getSimpleName().equals("Object")) {
			Field field[] = objClass.getDeclaredFields();
			for (Field f : field) {
				retList.add(f.getName());
			}
			objClass = objClass.getSuperclass();
		}
		return retList;
	}

	/**
	 * @Title: getAllPropertiesValue
	 * @Description: 获取对象属性值，存放在Map中
	 * @param @param AObject
	 * @param @return 设定文件
	 * @return Map<String,String> 返回类型
	 * @throws
	 */
	public static Map<String, String> getAllPropertiesValue(Object AObject) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> propList = getAllProperties(AObject);
		for (String f : propList) {
			Object objValue;
			try {
				objValue = DynamicProperty.getProperty(AObject, f);
				map.put(f, objValue == null ? "" : objValue.toString());
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * @Title: getAllPropertiesHaveValues
	 * @Description: 获取对象属性值(存在值的情况下)，存放在Map中
	 * @param @param AObject
	 * @param @return 设定文件
	 * @return Map<String,String> 返回类型
	 * @throws
	 */
	public static Map<String, String> getAllPropertiesHaveValues(Object AObject) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> propList = getAllProperties(AObject);
		for (String f : propList) {
			Object objValue;
			try {
				objValue = DynamicProperty.getProperty(AObject, f);
				if (null != objValue)
					map.put(f, objValue == null ? "" : objValue.toString());
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * @Title: getProperty
	 * @Description: 得到某个对象属性的值
	 * @param @param owner
	 * @param @param propertyName
	 * @param @return 设定文件
	 * @return Object 返回类型
	 * @throws
	 */
	public static Object getProperty(Object owner, String propertyName) {
		if (owner == null || "".equals(propertyName))
			return null;
		if ("class".equals(propertyName.toLowerCase()))
			propertyName = propertyName + "_";

		try {
			String propNameArray[] = propertyName.split("\\.");
			Object retObject = owner;
			for (int i = 0; i < propNameArray.length; i++) {
				Method method = retObject.getClass().getMethod(
						getFunctionName(propNameArray[i]));
				retObject = method.invoke(retObject);
			}

			return retObject;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Title: setField
	 * @Description: 设置字段的值
	 * @param @param owner
	 * @param @param fieldName
	 * @param @param value
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean setField(Object owner, String fieldName, Object value) {
		if (owner == null || owner.equals("") || fieldName == null
				|| fieldName.equals("")) {
			return false;
		}
		Class<?> ownerClass = owner.getClass();
		try {
			Field field = ownerClass.getField(fieldName);
			field.set(owner, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @Title: setProperty
	 * @Description: 通过字符串属性名称设置属性值
	 * @param @param target
	 * @param @param propertyName
	 * @param @param propertyValue
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean setProperty(Object target, String propertyName,
			Object propertyValue) {
		if (target == null || "".equals(propertyName))
			return false;

		if ("class".equals(propertyName.toLowerCase()))
			propertyName = propertyName + "_";

		try {
			// 找到最终的属性
			String propNameArray[] = propertyName.split("\\.");
			Object obj = target;
			for (int i = 0; i < propNameArray.length - 1; i++) {
				obj = getProperty(obj, propNameArray[i]);
			}
			if (null == obj)
				return false;

			// 获取属性的类型
			Class<?> lastPropertyClass = getPropertyType(obj,
					propNameArray[propNameArray.length - 1]);
			String objType = lastPropertyClass.getSimpleName();// obj.toString().substring(obj.toString().lastIndexOf(".")
			// + 1,
			// obj.toString().length());
			Object value = transType(objType, propertyValue);

			Method method = obj.getClass().getMethod(
					setFunctionName(propNameArray[propNameArray.length - 1]),
					lastPropertyClass);
			method.invoke(obj, new Object[] { value });
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	}

	/**
	 * @Title: getPropertyType
	 * @Description: 取得属性类型
	 * @param @param owner
	 * @param @param fieldName
	 * @param @return 设定文件
	 * @return Class<?> 返回类型
	 * @throws
	 */
	private static Class<?> getPropertyType(Object owner, String fieldName) {
		if (owner == null || fieldName == null || fieldName.equals(""))
			return null;
		try {
			Method method = owner.getClass().getMethod(
					getFunctionName(fieldName));
			return method.getReturnType();
		} catch (Exception ex) {
			String fieldName1 = fieldName.substring(0, 1).toLowerCase()
					+ fieldName.substring(1);
			try {
				Method method = owner.getClass().getMethod(
						getFunctionName(fieldName1));
				return method.getReturnType();
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @Title: transType
	 * @Description: 类型转换
	 * @param @param propertyNameType
	 * @param @param obj
	 * @param @return 设定文件
	 * @return Object 返回类型
	 * @throws
	 */
	private static Object transType(Object propertyNameType, Object obj) {
		if (null == obj || null == propertyNameType)
			return null;
		try {
			if (propertyNameType.toString().equalsIgnoreCase("string")) {
				obj = String.valueOf(obj);
			} else if (propertyNameType.toString().equalsIgnoreCase("long")) {
				obj = Long.valueOf(roundByString(String.valueOf(obj)));
			} else if (propertyNameType.toString().equalsIgnoreCase("int")) {
				obj = Integer.valueOf(roundByString(String.valueOf(obj)));
			} else if (propertyNameType.toString().equalsIgnoreCase("integer")) {
				obj = Integer.valueOf(roundByString(String.valueOf(obj)));
			} else if (propertyNameType.toString().equalsIgnoreCase("double")) {
				obj = Double.valueOf(String.valueOf(obj));
			} else if (propertyNameType.toString().equalsIgnoreCase("float")) {
				obj = Float.valueOf(String.valueOf(obj));
			} else if (propertyNameType.toString().equalsIgnoreCase("short")) {
				obj = Short.valueOf(String.valueOf(obj));
			} else if (propertyNameType.toString().equals("bigdecimal")) {
				obj = BigDecimal.valueOf(Double.valueOf(String.valueOf(obj)));
			} else if (propertyNameType.toString().equalsIgnoreCase("byte")) {
				obj = Byte.valueOf(String.valueOf(obj));
			} else if (propertyNameType.toString().equalsIgnoreCase("boolean")) {
				obj = ",true,1,".indexOf(String.valueOf(obj).toLowerCase()) > 0;
			} else if (propertyNameType.toString().equalsIgnoreCase("bool")) {
				obj = ",true,1,".indexOf(String.valueOf(obj).toLowerCase()) > 0;
			} else if (propertyNameType.toString().equalsIgnoreCase("date")) {
				try{
					obj = DateFormat.getDateInstance().parse(String.valueOf(obj));
				}catch(Exception e){
					//obj = DateFormat.getDateInstance().format(String.valueOf(obj));
				}
			} else if (propertyNameType.toString().equalsIgnoreCase("time")) {
				obj = Time.valueOf(String.valueOf(obj));
			} else if (propertyNameType.toString()
					.equalsIgnoreCase("timestamp")) {
				obj = Timestamp.valueOf(String.valueOf(obj));
			}
			return obj;
		} catch (Exception ex) {
			// ex.printStackTrace();
			return null;
		}
	}

	/**
	 * @Title: copyModuleObject
	 * @Description: 拷贝一个对象
	 * @param @param sourceObject 源对象
	 * @param @param targetObject 目标对象
	 * @param @param excludeFields 要排除的字段（属性
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean copyModuleObject(Object sourceObject,
			Object targetObject, String excludeFields) {
		if (sourceObject == null || targetObject == null) {
			return false;
		}
		String excField = "," + excludeFields.replaceAll(" ", "") + ",";
		List<String> sourcePropertiesList = getAllProperties(sourceObject);

		// Field field[] = targetObject.getClass().getDeclaredFields();
		List<String> filedList = getAllProperties(targetObject);
		for (String fieldName : filedList) {
			// 必须是源对象中存在的属性
			if (sourcePropertiesList.indexOf(fieldName) >= 0) {
				// 没有被排除的属性
				if (excField.indexOf("," + fieldName + ",") < 0) {
					Object sourceValue = getProperty(sourceObject, fieldName);
					// String sourceType = ht.get(fieldName); // 源对象属性类型
					Object targetValue = getProperty(targetObject, fieldName);
					// String targetType = f.getType().toString();// 目标对象属性类型
					// TODO:似乎需要对字符串类型去掉后面的空格进行比较
					// 两个属性如果类型一致情况下，才进行赋值(取消)
					// if (targetType.equals(sourceType))//
					if ((sourceValue == null && targetObject != null)
							|| (sourceValue != null && !sourceValue
									.equals(targetValue)))
						setProperty(targetObject, fieldName, sourceValue);
				}
			}
		}
		return true;
	}

	/**
	 * @Title: copyModuleObjectListProp
	 * @Description: 列表对象属性拷贝
	 * @param @param sourceObjectList 源对象
	 * @param @param targetObjectList 目录对象
	 * @param @param sourceProp 源列表对象属性
	 * @param @param targetProp 目标列表对象属性 <returns>返回目标对象</returns>
	 * @param @return 设定文件
	 * @return List 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static List copyModuleObjectListProp(List sourceObjectList,
			List targetObjectList, String sourceProp, String targetProp) {
		if (sourceObjectList == null || targetObjectList == null
				|| sourceProp == null || sourceProp.equals("")
				|| targetProp == null || targetProp.equals(""))
			return sourceObjectList;
		int minItemCount = Math.min(sourceObjectList.size(),
				targetObjectList.size());
		for (int i = 0; i < minItemCount; i++) {
			Object obj = DynamicProperty.getProperty(sourceObjectList.get(i),
					sourceProp);
			DynamicProperty.setProperty(targetObjectList.get(i), targetProp,
					obj);
		}
		return targetObjectList;
	}

	/**
	 * 保留方法： 对象列表拷贝:要求targetObjectList至少有一个元素，以便确定目标对象的类型
	 * 拷贝一个对象列表到新的对象列表之中,源对象和目标对象的类型可以不一致，只对属性名称相同的进行拷贝
	 * 调用例子：ModuleTools.copyModuleObjectList(源对象列表，目标对象列表)
	 * 
	 * @param sourceObjectList
	 *            :源对象列表
	 * @param targetObjectList
	 *            :目标对象列表
	 * @return
	 */
	/**
	 * @Title: copyModuleObjectList
	 * @Description: 保留方法： 对象列表拷贝:要求targetObjectList至少有一个元素，以便确定目标对象的类型
	 *               拷贝一个对象列表到新的对象列表之中,源对象和目标对象的类型可以不一致，只对属性名称相同的进行拷贝
	 *               调用例子：ModuleTools.copyModuleObjectList(源对象列表，目标对象列表)
	 * @param @param sourceObjectList 源对象列表
	 * @param @param targetObjectList 目标对象列表
	 * @param @return 设定文件
	 * @return List 返回类型
	 * @throws
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private static List copyModuleObjectList(List sourceObjectList,
			List targetObjectList) {
		Object tempObj = getEnumerableObjectType(targetObjectList);
		if (tempObj != null) {
			return copyModuleObjectList(sourceObjectList, targetObjectList,
					tempObj.getClass());
		}
		return null;
	}

	/**
	 * @Title: copyModuleObjectList
	 * @Description: 对象列表拷贝 拷贝一个对象列表到新的对象列表之中,源对象和目标对象的类型可以不一致，只对属性名称相同的进行拷贝
	 *               调用例子：ModuleTools.copyModuleObjectList(源对象列表，目标对象列表)
	 * @param @param sourceObjectList 源对象列表
	 * @param @param targetObjectList 目标对象列表
	 * @param @param targetClass 目标对象类型
	 * @param @return 设定文件
	 * @return List 返回类型
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List copyModuleObjectList(List sourceObjectList,
			List targetObjectList, Class targetClass) {
		if (sourceObjectList == null || targetObjectList == null
				|| targetClass == null)
			return null;

		for (Object obj : sourceObjectList) {
			Object newTarget = DynamicProperty.newInstance(targetClass
					.getName());
			DynamicProperty.copyModuleObject(obj, newTarget, "");
			targetObjectList.add(newTarget);
		}

		return targetObjectList;
	}

	// 返回IEnumerable列表中的真实对象的类型
	private static Object getEnumerableObjectType(List<Object> dataList) {
		if (dataList == null)
			return null;
		Object obj = null;
		if (dataList.size() > 0)
			obj = dataList.get(0);
		return obj;
	}

	// 截取数字字符串的整数部分，不进行四舍五入
	private static String roundByString(String s) {
		int index = s.indexOf(".");
		if (index < 0)
			return s;
		return s.substring(0, index);
	}

	private static String getFunctionName(String propertyName) {
		return "get" + propertyName.substring(0, 1).toUpperCase()
				+ propertyName.substring(1);
	}

	private static String setFunctionName(String propertyName) {
		return "set" + propertyName.substring(0, 1).toUpperCase()
				+ propertyName.substring(1);
	}
}
