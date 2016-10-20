package com.will.framework.util.tool.execl;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel 读取
 *                       
 * @Filename ExcelReader.java
 *
 * @Description 
 *
 *
 * @Email 
 *       

 *
 */
public interface ExcelReader {
	public static final int	INDEX_NOT_FOUND	= -1;
	
	/**
	 * 获取列名
	 * @return
	 */
	public String[] getHeaderInfo();
	
	/**
	 * 设置sheet
	 * @param index
	 */
	public void setWorkSheet(int index);
	
	public List<Object[]> read();
	
	public <T> List<T> read(Class<T> claz) throws InstantiationException, IllegalAccessException;
	
	public <T> List<T> read(Class<T> claz,int excludeRow) throws InstantiationException, IllegalAccessException;
	
	public <T> List<T> read(Class<T> claz, Map<String, String> map) throws InstantiationException,
																	IllegalAccessException;
	
	public <T> List<T> read(Class<T> claz, Map<String, String> map,int excludeRow) throws InstantiationException,
	IllegalAccessException;
	
	public int getColIndex(String propertName);
	
	public int getLastColIndex();
	
	Workbook getWorkbook();
}
