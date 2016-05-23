package com.will.framework.pojo;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 域对象(pojo)基类
 * 
 * @author will
 */
public class BasePojo implements Serializable {
	private static final long serialVersionUID = 1L;
	public BasePojo(){
		
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
