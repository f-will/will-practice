/**
 * 保存XML数据的对象
 */
package com.will.framework.util.xml;

import java.util.Hashtable;

/**
 * @author will
 * @category 保存XML数据的对象
 *
 */
public class XmlData {
	private String name;
	private String text;
	private Hashtable<String, String> attr;
	private Hashtable<String, XmlData> child;
	
	public XmlData(){
		
	}
	public XmlData(String name,String text){
		this.name=name;
		this.text=text;
	}
	/**
	 * @return 节点名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param 设置节点名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return 文本内容
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param 设置文本内容
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return  属性hash表
	 */
	public Hashtable<String, String> getAttr() {
		return attr;
	}
	/**
	 * @param 设置属性hash表
	 */
	public void setAttr(Hashtable<String, String> attr) {
		this.attr = attr;
	}
	/**
	 * @return 子节点
	 */
	public Hashtable<String, XmlData> getChild() {
		return child;
	}
	/**
	 * @param 设置子节点
	 */
	public void setChild(Hashtable<String, XmlData> child) {
		this.child = child;
	}
	
	
}
