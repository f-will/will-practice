package com.will.framework.pojo;

import java.io.Serializable;

/** 
* @ClassName: KeyValue 
* @Description: 键值类
* @author kill
*  
*/
@SuppressWarnings("serial")
public class KeyValue implements Serializable{
	private Object key;//键
	private Object value;//值
	public KeyValue(){
		
	}
	
	public KeyValue(Object key,Object value){
		this.key = key;
		this.value = value;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.key + ":" + this.value;
	}
}
