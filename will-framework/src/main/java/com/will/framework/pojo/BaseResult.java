package com.will.framework.pojo;

import org.apache.commons.lang.StringUtils;

import com.will.framework.constant.ResultValueEnum;

/**
 * 返回结果基类
 * @author will
 */
public class BaseResult<T> extends BasePojo {
	private static final long serialVersionUID = -9020398123705531740L;
	private int statusCode = 500;
	private String message;
	private T data;
	/**
	 * 创建成功结果实例
	 * @return 成功结果实例
	 */
	public static <T> BaseResult<T> newInstance() {
		return new BaseResult<T>(ResultValueEnum.SYS_OK);
	}
	/**
	 * 创建结果实例
	 * @param resultValue 枚举值
	 * @return BaseResult<T>
	 */
	public static <T> BaseResult<T> valueOf(ResultValueEnum resultValue) {
		return new BaseResult<T>(resultValue);
	}
	private BaseResult(ResultValueEnum resultValue) {
		super();
		this.statusCode = resultValue.getKey();
		this.message = resultValue.getTitle();
	}

	public BaseResult<T> changeStatus(ResultValueEnum resultValue) {
		this.statusCode = resultValue.getKey();
		this.message = resultValue.getTitle();
		return this;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public String getMessage() {
		return message;
	}

	public void addMessage(String msg) {
		//this.message += "-" + msg;
		if(StringUtils.isNotEmpty(msg)){
			this.message  = msg;	
		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	/**
	 * 是否成功
	 * @return 成功返回true，失败返回false
	 */
	public boolean isSucceed(){
		return this.getStatusCode()==ResultValueEnum.SYS_OK.getKey();
	}
}
