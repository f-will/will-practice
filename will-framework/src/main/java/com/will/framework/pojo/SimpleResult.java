package com.will.framework.pojo;

import com.will.framework.constant.ResultValueEnum;

/**
 * 简单结果类
 * @author will
 */
public class SimpleResult extends BasePojo {
	private static final long serialVersionUID = -9020398123705531740L;
	private int statusCode = 500;
	private String message;
	
	public SimpleResult(int statusCode,String message){
		this.statusCode=statusCode;
		this.message=message;
	}
	public SimpleResult(ResultValueEnum resultValue) {
		this(resultValue.getKey(),resultValue.getTitle());
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void addMessage(String msg) {
		this.message += "-" + msg;
	}
	public SimpleResult changeStatus(ResultValueEnum resultValue) {
		this.statusCode = resultValue.getKey();
		this.message = resultValue.getTitle();
		return this;
	}
	/**
	 * 是否成功
	 * @return 成功返回true，失败返回false
	 */
	public boolean isSucceed(){
		return this.getStatusCode()==ResultValueEnum.SYS_OK.getKey();
	}
}
