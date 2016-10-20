package com.will.framework.util.http;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 控制器工具类
 * @author will
 */
public class ControllerUtil {
	private static Log log=LogFactory.getLog(ControllerUtil.class);

	private ControllerUtil() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 打印XML
	 * produces={"text/xml;charset=UTF-8"}
	 *@param response {@link HttpServletResponse}
	 *@param content     xml内容
	 *@throws IOException
	 */
	public static void printXml(HttpServletResponse response, String content)
			throws IOException {
		log.debug(content);
		response.setContentType("text/xml;charset=UTF-8");
		response.getWriter().print(content);
	}
	/**
	 * 打印Json
	 * produces={"application/json;charset=UTF-8"}
	 *@param response {@link HttpServletResponse}
	 *@param content  Json内容
	 *@throws IOException
	 */
	public static void printJson(HttpServletResponse response, String content)
			throws IOException {
		log.debug(content);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(content);
	}
	/**
	 * 打印HTML
	 * produces={"text/html;charset=UTF-8"}
	 *@param response {@link HttpServletResponse}
	 *@param content  HTML内容
	 *@throws IOException
	 */
	public static void printHtml(HttpServletResponse response, String content)
			throws IOException {
		log.debug(content);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(content);
	}
	
	/**
	 * 获取当前项目的绝对地址
	 *@param request {@link HttpServletRequest}
	 *@return        当前项目的绝对地址
	 */
	public static String getRealPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
	/**
	 * 显示请求参数
	 *@param request {@link HttpServletRequest}
	 */
	public static void showRequestParameters(HttpServletRequest request) {
		Enumeration<?> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement().toString();
			String paramValue = request.getParameter(paramName);
			log.info(paramName + ":" + paramValue);
		}
	}
	/**
	 * 创建spring重定向地址视图
	 *@param url 原始url地址
	 *@return    spring视图
	 *@author 罗家友
	 */
	public static String createRedirectView(String url){
		return "redirect:"+url;
	}
	/**
	 * 取客户端IP
	 * @return IP地址
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}
}
