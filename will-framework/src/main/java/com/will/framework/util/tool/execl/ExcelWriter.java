package com.will.framework.util.tool.execl;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 
 *                       
 * @Filename ExcelWrite.java
 *
 * @Description 
 *
 * @Version 1.0
 *
 *       
 *
 */
public interface ExcelWriter {
	
	public <T> void fillSheet(String[] headerInfo, List<T> list);
	
	public <T> void fillSheet(String[] headerInfo, List<T> list, Map<String, String> propertyMap);
	
	public void fillSheetStr(String[] headerInfo, List<String[]> list);
	
	public <T> void setCellValue(int row, int cell, T t);
	
	public void setCellErrorComment(int row, int cell, String msg);
	
	public void setRowErrorComment(int row, String msg);
	
	public void setLastCellErrorComment(int row,int lastCell,String msg);
	
	public void write(OutputStream out) throws IOException;
	
	public int delRow(int row);
	
	public int getColIndex(String propertName);
	
	public int getLastColIndex();
	
}
