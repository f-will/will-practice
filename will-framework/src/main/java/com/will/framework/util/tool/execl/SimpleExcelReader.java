package com.will.framework.util.tool.execl;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;




/**
 * 
 *                       
 * @Filename SimpleExcelReader.java
 *
 * @Description 
 *
 * @Version 1.0
 *
 *

 *       

 *
 */
public class SimpleExcelReader implements ExcelReader {
	
	private Workbook				wb;										// workbook
																				
	private Sheet					sheet;										// 当前 sheet
																				
	private String[]				headerInfo;								// 头信息 当前sheet的头信息
																				
	private Map<String, Integer>	proIndex;									//列信息
																				
	@SuppressWarnings("unused")
	private Log						log	= LogFactory.getLog(this.getClass());
	
	public SimpleExcelReader(InputStream in) throws IllegalArgumentException,
											InvalidFormatException, IOException {
		wb = WorkbookFactory.create(in);
		setWorkSheet(0);
	}
	
	@Override
	public String[] getHeaderInfo() {
		return headerInfo;
	}
	
	@Override
	public void setWorkSheet(int index) {
		sheet = wb.getSheetAt(index);
		setHeaderInfo();
	}
	
	@Override
	public List<Object[]> read() {
		List<Object[]> list = new ArrayList<Object[]>();
		
		Iterator<Row> it = sheet.iterator();
		if (it.hasNext())
			it.next();// break first line
		while (it.hasNext()) {
			Row row = it.next();
			list.add(getRowValue(row));
		}
		
		return list;
	}
	
	@Override
	public <T> List<T> read(Class<T> claz) throws InstantiationException, IllegalAccessException {
		return this.read(claz,1);
	}
	
	@Override
	public <T> List<T> read(Class<T> claz, int excludeRow)
			throws InstantiationException, IllegalAccessException {
		Map<String, String> map = new HashMap<String, String>();
		// 根据 title注解 生成 map
		Field[] fields = claz.getDeclaredFields();
		T t = claz.newInstance();
		for (Field f : fields) {
			if (!PropertyUtils.isWriteable(t, f.getName())) {
				continue;
			}
			Title tt = f.getAnnotation(Title.class);
			if (null == tt)
				continue;
			map.put(tt.value(), f.getName());
			
		}
		
		return this.read(claz, map,excludeRow);
	}
	
	@Override
	public <T> List<T> read(Class<T> claz, Map<String, String> map) throws InstantiationException,
																	IllegalAccessException {
		return this.read(claz, map, 1);
	}
	
	/**
	 * 得到 属性的 列序号
	 * @param propertName  属性名称
	 * @return
	 */
	@Override
	public int getColIndex(String propertName) {
		if (proIndex == null) {
			throw new ExcelException("please run read method first!");
		}
		Integer i = proIndex.get(propertName);
		if (null == i) {
			return INDEX_NOT_FOUND;
		}
		return i;
	}
	
	@Override
	public Workbook getWorkbook() {
		return wb;
	}
	
	@Override
	public int getLastColIndex() {
		return this.getHeaderInfo().length - 1;
	}
	
	private void setHeaderInfo() {
		Row row = sheet.getRow(0);
		Object[] os = getRowValue(row);
		headerInfo = Arrays.asList(os).toArray(new String[0]);
		
	}
	
	private Object[] getRowValue(Row row) {
		Object[] s = new Object[row.getLastCellNum()];
		for (int i = 0; i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			if (cell == null)
				continue;
			s[i] = getCellValue(cell);
		}
		
		return s;
	}
	
	private Object getCellValue(Cell cell) {
		Object s = null;
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				s = StringUtils.trim(cell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				s = cell.getNumericCellValue();
				break;
			default:
				s = cell.getStringCellValue();
				break;
		}
		return s;
	}
	
	private String getCellStringValue(Cell cell) {
		String s = null;
		DecimalFormat nf = new DecimalFormat("0");
		
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				double dv = cell.getNumericCellValue();
				
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = DateUtil.getJavaDate(dv);
					String dateFmt = cell.getCellStyle().getDataFormatString();
					s = new CellDateFormatter(dateFmt).format(date);
				} else {
					s = nf.format(cell.getNumericCellValue());
				}
				
				break;
			case Cell.CELL_TYPE_STRING:
			default:
				s = cell.getStringCellValue();
				break;
		}
		s = StringUtils.trim(s);
		return s;
	}
	
	private double getCellDoubleValue(Cell cell) {
		double d = 0;
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				d = cell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				try { 
					d = cell.getNumericCellValue();
		        } catch (IllegalStateException e) {  
//	                String strCell = String.valueOf(cell.getRichStringCellValue()); 
		        } 
				break;
			case Cell.CELL_TYPE_STRING:
			default:
				d = NumberUtils.toDouble(cell.getStringCellValue());
				break;
		}
		
		return d;
	}
	
	private int getCellIntValue(Cell cell) {
		int i = 0;
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				i = (int) Math.floor(cell.getNumericCellValue() + 0.5d);
				break;
			case Cell.CELL_TYPE_FORMULA:
				try { 
					double d = cell.getNumericCellValue();
					i = (int)d;
		        } catch (IllegalStateException e) {  
//	                String strCell = String.valueOf(cell.getRichStringCellValue()); 
		        } 
				break;
			case Cell.CELL_TYPE_STRING:
			default:
				i = NumberUtils.toInt(cell.getStringCellValue());
				break;
		}
		
		return i;
	}
	
	private Date getCellDateValue(Cell cell) {
		Date d = null;
		final String[] pattenrs = new String[] { "yyyy-MM", "yyyy-MM-dd", "yyyy/MM", "yyyy/MM/dd",
				"yyyy.MM", "yyyy.MM.dd", "yyyy年MM月", "yyyy年MM月dd日" };
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				d = cell.getDateCellValue();
				break;
			case Cell.CELL_TYPE_STRING:
			default:
				try {
					d = DateUtils.parseDate(cell.getStringCellValue(), pattenrs);
				} catch (Exception e) {
					
				}
				break;
		}
		
		return d;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public <T> List<T> read(Class<T> claz, Map<String, String> map,
			int excludeRow) throws InstantiationException,
			IllegalAccessException {
		List<T> list = new ArrayList<T>();
		if (MapUtils.isEmpty(map))
			return list;
		Iterator<Row> it = sheet.iterator();
		//根据 map 设置列给属性赋值
		Map<Integer, String> propertyIndex = new HashMap<Integer, String>();
		
		Set<String> ts = map.keySet();
		for (String title : ts) {
			int index = ArrayUtils.indexOf(headerInfo, title);
			if (index == ArrayUtils.INDEX_NOT_FOUND) {
				continue;
			}
			propertyIndex.put(index, map.get(title));
			
		}
		if (MapUtils.isEmpty(propertyIndex)) {
			return list;
		}
		proIndex = MapUtils.invertMap(propertyIndex);
		
		if (it.hasNext())
			for (int i = 0; i < excludeRow; i++) {
				it.next();// break  line
			}
		while (it.hasNext()) {
			Row row = it.next();
			T t = claz.newInstance();
			for (short i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
				Cell cell = row.getCell(i);
				if (cell == null) {
					continue;
				}
				String propertyName = propertyIndex.get((int) i);
				if (StringUtils.isEmpty(propertyName)) {
					continue;
				}
				Class<?> pclaze = null;
				try {
					pclaze = PropertyUtils.getPropertyType(t, propertyName);
				} catch (Exception e) {
					continue;
				}
				String simpleName = pclaze.getSimpleName();
				
				// 给属性设置值
				try {
					if ("String".equals(simpleName)) {
						PropertyUtils.setProperty(t, propertyName, getCellStringValue(cell));
					} else if ("Double".equals(simpleName)) {
						PropertyUtils.setProperty(t, propertyName, getCellDoubleValue(cell));
					} else if ("Integer".equals(simpleName)) {
						PropertyUtils.setProperty(t, propertyName, getCellIntValue(cell));
					} else if ("Date".equals(simpleName)) {
						PropertyUtils.setProperty(t, propertyName, getCellDateValue(cell));
					} else {
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			list.add(t);
			
		}
		
		return list;
	}

	
	
	
	
}
