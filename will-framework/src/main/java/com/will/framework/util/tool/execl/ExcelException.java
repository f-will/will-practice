package com.will.framework.util.tool.execl;

/**
 * 
 *
 */
public class ExcelException extends RuntimeException {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long	serialVersionUID	= -1325769410348659900L;
	
	public ExcelException() {
		super();
	}
	
	public ExcelException(String message) {
		super(message);
	}
	
	public ExcelException(String message, Throwable cause) {
		super(message, cause);
	}
}