package com.will.framework.util.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Html正则表达式工具类
 * 
 * @author will
 */
public abstract class HtmlRegexpUtil {
	private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签
	// private final static String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>";
	// // 找出IMG标签
	// private final static String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\"";
	// // 找出IMG标签的SRC属性

	/**
	 * 将html标记转义
	 * @param html html字符串
	 * @return     转以后的html字符串
	 * @author 罗家友
	 */
	public static String escapeHtml(String html) {
		return StringEscapeUtils.escapeHtml(html);
	}
	/**
	 * 将转义后的html恢复
	 *@param html   转义后的html
	 *@return       恢复后的html
	 *@author 罗家友
	 */
	public static String unescapeHtml(String html) {
		return StringEscapeUtils.unescapeHtml(html);
	}

	/**
	 * 清除所有以"<"开头以">"结尾的标签
	 *@param html html字符串
	 *@return     清除后的html字符串
	 *@author 罗家友
	 */
	public static String cleanHtmlTag(String html) {
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(html);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 清除指定标签
	 *@param html  html字符串
	 *@param tag   标签。比如img
	 *@return      清除标签和标签内容的html字符串
	 *@author 罗家友
	 */
	public static String cleanHtmlTag(String html, String tag) {
		String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";
		String endTag="</"+tag+">";
		Pattern pattern = Pattern.compile(regxp);
		return pattern.matcher(html).replaceAll("").replace(endTag, "");
	}

	/**
	 * 
	 * 基本功能：替换指定的标签
	 * <p>
	 * 
	 * @param str
	 * @param beforeTag
	 *            要替换的标签
	 * @param tagAttrib
	 *            要替换的标签属性值
	 * @param startTag
	 *            新标签开始标记
	 * @param endTag
	 *            新标签结束标记
	 * @return String
	 * @如：替换img标签的src属性值为[img]属性值[/img]
	 */
	public static String replaceHtmlTag(String str, String beforeTag,
			String tagAttrib, String startTag, String endTag) {
		String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
		String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";
		Pattern patternForTag = Pattern.compile(regxpForTag);
		Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
		Matcher matcherForTag = patternForTag.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result = matcherForTag.find();
		while (result) {
			StringBuffer sbreplace = new StringBuffer();
			Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag
					.group(1));
			if (matcherForAttrib.find()) {
				matcherForAttrib.appendReplacement(sbreplace, startTag
						+ matcherForAttrib.group(1) + endTag);
			}
			matcherForTag.appendReplacement(sb, sbreplace.toString());
			result = matcherForTag.find();
		}
		matcherForTag.appendTail(sb);
		return sb.toString();
	}

	public static void main(String[] args) {
		String html = "<title>AB,CD。</title>g%%sdggas￥#$<title></title><p>luo.、\\jiayoou</p>jkll<title>005</title><img alt=\"\" src=\"http://ticket.online.cq.cn/image/cinema/2011/10/4/1318846214439.jpg\" style=\"width: 200px; height: 130px\"></img>";
		System.out.println(cleanHtmlTag(html));
	}

}
