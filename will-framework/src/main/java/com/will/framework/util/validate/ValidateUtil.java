package com.will.framework.util.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.will.framework.util.http.HttpClientHelper;
import com.will.framework.util.json.JsonUtil;

/**
 * 
 * <p>
 * Description: 各种验证方法类
 * </p>
 * 
 * @author will
 */
public class ValidateUtil {
	/**
	 * 邮箱验证
	 * 
	 * @param email
	 *            邮箱地址
	 * @return 验证成功返回true，失败返回false
	 */
	public static boolean isEmail(String email) {
		if (null == email)
			return false;
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	/**
	 * 验证座机号码
	 * 
	 * @param tel
	 *            电话
	 * 7~8位数直接号码或者3~4位区号加-直接号码
	 * @return 验证成功返回true，失败返回false
	 */
	public static boolean isTelPhoneNum(String tel) {
		if (null == tel)
			return false;
		final String regex = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";
		return tel.matches(regex);
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobile
	 *            手机号码
	 * @return 验证成功返回true，失败返回false
	 */
	public static boolean isMobilePhoneNum(String mobile) {
		if (null == mobile)
			return false;
		String regex1 = "^13\\d{9}";
		String regex4 = "^14\\d{9}";
		String regex2 = "^15\\d{9}";
		String regex3 = "^18\\d{9}";
		String regex5 = "^17\\d{9}";
		if (mobile.matches(regex1) || mobile.matches(regex2)
				|| mobile.matches(regex3) || mobile.matches(regex4)|| mobile.matches(regex5)) {
			return true;
		}
		return false;
	}

	/**
	 * 验证是否为联通手机号码
	 * 
	 * @param phone
	 *            手机号码
	 * @return 验证成功返回true，失败返回false
	 */
	public static boolean isCUCCPhone(String phone) {
		if (null == phone)
			return false;
		String[] regx = new String[] { "^13[0,1,2]\\d{8}", "^145\\d{8}",
				"^15[5,6]\\d{8}", "^18[5,6]\\d{8}" };
		for (String reg : regx) {
			if (phone.matches(reg)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证是否为移动手机号码
	 * 
	 * @param phone
	 *            手机号码
	 * @return 验证成功返回true，失败返回false
	 */
	public static boolean isCMCCPhone(String phone) {
		if (null == phone)
			return false;
		String[] regx = new String[] { "^13[4,5,6,7,8,9]\\d{8}", "^147\\d{8}",
				"^15[0,1,2,7,8,9]\\d{8}", "^18[2,7,8]\\d{8}" };
		for (String reg : regx) {
			if (phone.matches(reg)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证是否为电信手机号码
	 * 
	 * @param phone
	 *            手机号码
	 * @return 验证成功返回true，失败返回false
	 */
	public static boolean isCTCCPhone(String phone) {
		if (null == phone)
			return false;
		String[] regx = new String[] { "^180\\d{8}", "^181\\d{8}",
				"^189\\d{8}", "^153\\d{8}","^177\\d{8}","^133\\d{8}" };
		for (String reg : regx) {
			if (phone.matches(reg)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证是否是数字(不能以0开始和非小数数字)
	 * 
	 * @param str
	 *            需要验证的字符串
	 * @return 验证成功返回true，失败返回false
	 */
	public static boolean isNumber(String str) {
		if (null == str)
			return false;
		String regex = "^\\+?[1-9][0-9]*$";
		return str.matches(regex);
	}

	/**
	 * 验证是否是数字(非小数，可以是0开始)
	 * 
	 * @param str
	 *            需要验证的字符串
	 * @return 验证成功返回true，失败返回false
	 */
	public static boolean isNumberString(String str) {
		if (null == str)
			return false;
		String regex = "^[0-9]*$";
		return str.matches(regex);
	}

	/**
	 * 验证是否是double类型(非负)
	 * 
	 * @param str
	 *            需要验证的字符串
	 * @return 验证成功返回true，失败返回false
	 */
	public static boolean isDouble(String str) {
		if (null == str)
			return false;
		String regex = "^[0-9]+(.\\d+)*";
		return str.matches(regex);
	}

	/**
	 * 验证日期格式yyyy-MM-dd
	 * 
	 * @param date
	 *            需要验证的字符串
	 * @return 格式正确返回true,失败返回false
	 */
	public static boolean isDateFormat(String date) {
		if (null == date)
			return false;
		String regex = "^\\d{4}[\\/\\-]\\d{1,2}[\\/\\-]\\d{1,2}$";
		return date.matches(regex);
	}

	/**
	 * 验证中文
	 * 
	 * @param str
	 *            需要验证的字符串
	 * @return 验证成功返回true，失败返回false
	 */
	public static boolean isChineseWord(String str) {
		if (null == str)
			return false;
		String regex = "^[\u4e00-\u9fa5]+$";
		return str.matches(regex);
	}

	/**
	 * 验证英文
	 * 
	 * @param str
	 *            需要验证的字符串
	 * @return 验证成功返回true，失败返回false
	 */
	public static boolean isEnglishWord(String str) {
		if (null == str)
			return false;
		String regex = "^[a-zA-Z\\ \']+$";
		return str.matches(regex);
	}

	/**
	 * 验证只能是英文和数字
	 * 
	 * @param str
	 *            需要验证的字符串
	 * @return 验证成功返回true，失败返回false
	 */
	public static boolean isOnlyLetterNumber(String str) {
		if (null == str)
			return false;
		String regex = "^[0-9a-zA-Z]+$";
		return str.matches(regex);
	}

	/**
	 * 验证以英文开头只能是英文和数字
	 *
	 * @param str
	 *            需要验证的字符串
	 * @return
	 * @author 罗家友
	 */
	public static boolean isFirstLetterOnlyLetterNumber(String str) {
		if (null == str)
			return false;
		String regex = "^[a-zA-Z][0-9a-zA-Z]*$";
		return str.matches(regex);
	}
	/**
	 * 判断多个参数中某个是否为空
	 * 如果一个为空返回true，都不为空返回false
	 * @param objs 需验证的参数列表
	 * @return 如果一个为空返回true，都不为空返回false
	 */
	public static boolean isEmpty(Object... objs){
		for(Object obj:objs){
			if(StringUtils.isEmpty(obj)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断多个boolean值其中是否有为false的
	 * 如果一个为false，返回true，全部为true返回false
	 * @param flags 需验证标示参数列表
	 * @return 如果一个为false，返回true，全部为true返回false
	 */
	public static boolean isFalse(boolean... flags){
		for(boolean flag:flags){
			if(!flag){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断多个boolean值其中是否有为true的
	 * 如果一个为true，返回true，全部为false返回false
	 * @param flags 需验证标示参数列表
	 * @return 如果一个为true，返回true，全部为false返回false
	 */
	public static boolean isTrue(boolean... flags){
		for(boolean flag:flags){
			if(flag){
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证身份证号码是否合法
	 *
	 * @param idCardNo
	 *            身份证号码
	 * @return 合法返回true，不合法返回false
	 */
	public static boolean isIdCardNo(String idCardNo) {
		if (null == idCardNo)
			return false;
		String pattern = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(idCardNo);
		return m.matches();

	}

	/**
	 * 判断是否为合法IP
	 * 
	 * @return the ip
	 */
	public static boolean isIp(String ipAddress) {
		if (null == ipAddress)
			return false;
		String ip = "^((2[0-4]\\d|25[0-5]|[1-9]?\\d|1\\d{2})\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}
	
	/**
	 * 数字字母混淆，长度6~16位
	 * @param inputStr
	 * @return
	 */
	public static boolean isNoAndChar(String inputStr){
		if(null==inputStr)return false;
		
		String regx = "^[A-Za-z0-9]{6,16}$";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();
	}
	/**
	 * 全是字母长度6~16位
	 * @param inputStr
	 * @return
	 */
	public static boolean isAllChar(String inputStr){
		if(null==inputStr)return false;
		
		String regx = "^[A-Za-z]{6,16}$";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();
	}
	/**
	 * 是否为普通账户
	 * @return
	 */
	public static boolean isNomalUser(String loginName){
		if(StringUtils.isEmpty(loginName))return false;
		//是否为电话
		if(isMobilePhoneNum(loginName)){return true;}
		//是否为Email
		if(isEmail(loginName)){return true;}
		//字母+数字、字母
		return (isNoAndChar(loginName)||isAllChar(loginName));
	}
	
	/**
	 * 是否为易生卡
	 * @param inputStr	卡号
	 * @return
	 */
	public static boolean isYskNo(String inputStr){
		if(null==inputStr)return false;
		
		String regx = "^[^0]{1}\\d{17,24}$";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(inputStr);
		
		return matcher.matches();
	}
	
	/**
	 * 通过阿里巴巴验证手机号，获取手机的基本信息
	 * @param phone		电话号码
	 * @return
	 */
	public static AlibabaPhoneResponse phoneFromAlibaba(String phone){
		if(!ValidateUtil.isMobilePhoneNum(phone)){
			
			return null;
		}
		StringBuffer sb = new StringBuffer("http://tcc.taobao.com/cc/json/mobile_tel_segment.htm");
		sb.append("?tel="+phone);
		try {
			String jsonString = HttpClientHelper.doGet(sb.toString());
			if(!StringUtils.isEmpty(jsonString)){
				int startIndx = jsonString.indexOf("{");
				
				if(-1==startIndx)return null;
				jsonString = jsonString.substring(startIndx);
				//获取阿里巴巴手机验证
				return JsonUtil.parseObject(jsonString, AlibabaPhoneResponse.class);
			}
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private ValidateUtil(){}
}
