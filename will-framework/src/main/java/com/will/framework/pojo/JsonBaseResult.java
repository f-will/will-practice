package com.will.framework.pojo;


/**
 * Json返回结果基类
 * 这个类用作BaseResult输出json字符串后
 * 再将该json字符串解析为对象时使用
 * @author will
 */
public class JsonBaseResult<T> extends BasePojo {
	private static final long serialVersionUID = 3267462410453926515L;
	private int statusCode = 500;
	private String message;
	private T data;
	public JsonBaseResult(){}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
