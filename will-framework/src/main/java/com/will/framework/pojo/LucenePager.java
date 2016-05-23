package com.will.framework.pojo;

import org.apache.lucene.document.Document;
/**
 * lucene分页类
 * @author will
 * @param <T> lucene文档转换为实体对象的类型
 */
public abstract class LucenePager<T> extends Pager<T> {
	private static final long serialVersionUID = 4794361882684242833L;
	public LucenePager(){
		
	}
	/**
	 * 将lucene文档解析为指定实体对象逻辑
	 * @param doc lucene文档
	 * @return <T>
	 */
	public abstract T parseDocument(Document doc);
}
