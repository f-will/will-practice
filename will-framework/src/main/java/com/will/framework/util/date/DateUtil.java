package com.will.framework.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 【时间工具】
 * 
 * @author will
 **/
public class DateUtil {
	/** 定义常量 **/

	/**yyyyMM*/
	public static final String DATE_JFP_STR = "yyyyMM";
	/**yyyy-MM-dd HH:mm:ss**/
	public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
	/**yyyy-MM-dd**/
	public static final String DATE_SMALL_STR = "yyyy-MM-dd";
	/**yyMMddHHmmss**/
	public static final String DATE_KEY_STR = "yyMMddHHmmss";
	/**全年月日时分秒**/
	public static final String DATE_FULL_STRX = "yyyyMMddHHmmss";

	/**
	 * 获取当日凌晨时间
	 */
	public static Date getToday() {
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.HOUR_OF_DAY, 0);
		cld.set(Calendar.MINUTE, 0);
		cld.set(Calendar.SECOND, 0);
		cld.set(Calendar.MILLISECOND, 0);
		return cld.getTime();
	}

	/**
	 * 获取当日结束时刻23:59:59.999
	 */
	public static Date getEndOfDay() {
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.HOUR_OF_DAY, 23);
		cld.set(Calendar.MINUTE, 59);
		cld.set(Calendar.SECOND, 59);
		cld.set(Calendar.MILLISECOND, 999);
		return cld.getTime();
	}

	/**
	 * 获取距离现在指定天数的某天的开始时刻00:00:00.000
	 * 
	 * @param interval
	 *            间隔
	 * @return Date java.util.Date
	 * 
	 */
	public static Date getStartOfDay(int interval) {
		Calendar cld = Calendar.getInstance();
		cld.setTimeInMillis(cld.getTimeInMillis() + interval * 24 * 60 * 60
				* 1000l);
		cld.set(Calendar.HOUR_OF_DAY, 0);
		cld.set(Calendar.MINUTE, 0);
		cld.set(Calendar.SECOND, 0);
		cld.set(Calendar.MILLISECOND, 0);
		return cld.getTime();
	}

	/**
	 * 获取某天的开始时刻00:00:00.000
	 * 
	 * @param date
	 *            需要获取天内的时间
	 * @return Date java.util.Date
	 */
	public static Date getStartOfDay(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.set(Calendar.HOUR_OF_DAY, 0);
		cld.set(Calendar.MINUTE, 0);
		cld.set(Calendar.SECOND, 0);
		cld.set(Calendar.MILLISECOND, 0);
		return cld.getTime();
	}

	/**
	 * 获取距离现在指定天数的某天的结束时刻23:59:59.999
	 * 
	 * @param interval
	 *            间隔
	 * @return Date java.util.Date
	 */
	public static Date getEndOfDay(int interval) {
		Calendar cld = Calendar.getInstance();
		cld.setTimeInMillis(cld.getTimeInMillis() + interval * 24 * 60 * 60
				* 1000l);
		cld.set(Calendar.HOUR_OF_DAY, 23);
		cld.set(Calendar.MINUTE, 59);
		cld.set(Calendar.SECOND, 59);
		cld.set(Calendar.MILLISECOND, 999);
		return cld.getTime();
	}

	/**
	 * 获取某天的最后时刻23:59:59.999
	 * 
	 * @param date
	 *            需要获取天内的时间
	 * @return Date java.util.Date
	 */
	public static Date getEndOfDay(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.set(Calendar.HOUR_OF_DAY, 23);
		cld.set(Calendar.MINUTE, 59);
		cld.set(Calendar.SECOND, 59);
		cld.set(Calendar.MILLISECOND, 999);
		return cld.getTime();
	}

	/**
	 * 获取距离现在指定星期数的某个星期的开始时刻00:00:00.000
	 * 
	 * @param interval
	 *            间隔
	 * @return Date java.util.Date
	 */
	public static Date getStartOfWeek(int interval) {
		Calendar cld = Calendar.getInstance();
		cld.setTimeInMillis(cld.getTimeInMillis() + interval * 7 * 24 * 60 * 60
				* 1000l);
		cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取指定星期的开始时刻00:00:00.000
	 * 
	 * @param week
	 *            一年中的第几周
	 * @param year
	 *            年份
	 * @return Date java.util.Date
	 */
	public static Date getStartOfWeek(int year, int week) {
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.YEAR, year);
		cld.set(Calendar.WEEK_OF_YEAR, week);
		cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取距离指定日期指定星期数的某个星期的开始时刻00:00:00.000
	 * 
	 * @param interval
	 *            间隔
	 * @param date
	 *            日期
	 * @return Date java.util.Date
	 */
	public static Date getStartOfWeek(Date date, int interval) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.setTimeInMillis(cld.getTimeInMillis() + interval * 7 * 24 * 60 * 60
				* 1000l);
		cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取指定时间所在周的第一天的00:00:00.000
	 * 
	 * @param date
	 *            需要获取周的某天
	 * @return Date java.util.Date
	 */
	public static Date getStartOfWeek(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取距离现在指定周数的某周的最后时刻23:59:59.999
	 * 
	 * @param interval
	 *            间隔
	 * @return Date java.util.Date
	 */
	public static Date getEndOfWeek(int interval) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(getStartOfWeek(interval + 1));
		return new Date(cld.getTimeInMillis() - 1);
	}

	/**
	 * 获取指定周数的最后时刻23:59:59.999
	 * 
	 * @param week
	 *            一年中的第几周
	 * @param year
	 *            年份
	 * @return Date java.util.Date
	 */
	public static Date getEndOfWeek(int year, int week) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(getStartOfWeek(year, week + 1));
		return new Date(cld.getTimeInMillis() - 1);
	}

	/**
	 * 获取距离指定日期指定周数的某周的最后时刻23:59:59.999
	 * 
	 * @param interval
	 *            间隔
	 * @param date
	 *            日期
	 * @return Date java.util.Date
	 */
	public static Date getEndOfWeek(Date date, int interval) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.setTime(getStartOfWeek(date, interval + 1));
		return new Date(cld.getTimeInMillis() - 1);
	}

	/**
	 * 获取指定时间所在周的最后一天的23:59:59.999
	 * 
	 * @param date
	 *            需要获取周的某天
	 * @return Date java.util.Date
	 */
	public static Date getEndOfWeek(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		return getEndOfDay(cld.getTime());
	}

	/**
	 * 获取指定时间所在月的第一天的00:00:00.000
	 * 
	 * @param date
	 *            需要获取月的某天
	 * @return Date java.util.Date
	 */
	public static Date getStartOfMonth(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.set(Calendar.DAY_OF_MONTH, 1);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取某年某月的开始时刻00:00:00.000
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return Date java.util.Date
	 */
	public static Date getStartOfMonth(int year, int month) {
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.YEAR, year);
		cld.set(Calendar.MONTH, month);
		cld.set(Calendar.DAY_OF_MONTH, 1);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取距离现在指定月数的某月的开始时刻00:00:00.000
	 * 
	 * @param interval
	 *            间隔
	 * @return Date java.util.Date
	 */
	public static Date getStartOfMonth(int interval) {
		Calendar cld = Calendar.getInstance();
		int currentMonth = cld.get(Calendar.MONTH); // 这里得到的值是0～11
		cld.add(Calendar.YEAR, ((interval + currentMonth) / 12));
		cld.set(Calendar.MONTH, ((interval + currentMonth) % 12));
		cld.set(Calendar.DAY_OF_MONTH, 1);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取指定时间所在月的最后一天的23:59:59.999
	 * 
	 * @param date
	 *            需要获取月的某天
	 * @return Date java.util.Date
	 */
	public static Date getEndOfMonth(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		int maxDay = cld.getActualMaximum(Calendar.DAY_OF_MONTH);
		cld.set(Calendar.DAY_OF_MONTH, maxDay);
		return getEndOfDay(cld.getTime());
	}

	/**
	 * 获取距离现在指定月数的某月的结束时刻23:59:59.999
	 * 
	 * @param interval
	 *            间隔
	 * @return Date java.util.Date
	 */
	public static Date getEndOfMonth(int interval) {
		return new Date(getStartOfMonth(interval + 1).getTime() - 1);
	}

	/**
	 * 获取某年某月的结束时刻23:59:59.999
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return Date java.util.Date
	 */
	public static Date getEndOfMonth(int year, int month) {
		return new Date(getStartOfMonth(year, month + 1).getTime() - 1);
	}

	/**
	 * 获取2个时间点之间的月份数
	 * 
	 * @param startDate
	 *            endDate
	 * @return int
	 */
	public static int monthsBetween(Date startDate, Date endDate) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(startDate);
		int startMonth = cld.get(Calendar.MONTH);
		int startYear = cld.get(Calendar.YEAR);
		cld.setTime(endDate);
		int endMonth = cld.get(Calendar.MONTH);
		int endYear = cld.get(Calendar.YEAR);
		return (endYear - startYear) * 12 + (endMonth - startMonth);
	}

	/**
	 * 获取2个时间点之间的天数
	 * 
	 * @param startDate
	 *            endDate
	 * @return int
	 */
	public static int daysBetween(Date startDate, Date endDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(startDate);
		c2.setTime(endDate);
		return daysBetween(c1, c2);
	}

	public static int daysBetween(Calendar early, Calendar late) {
		return (int) (toJulian(late) - toJulian(early));
	}

	public static final float toJulian(Calendar c) {
		int Y = c.get(Calendar.YEAR);
		int M = c.get(Calendar.MONTH);
		int D = c.get(Calendar.DATE);
		int A = Y / 100;
		int B = A / 4;
		int C = 2 - A + B;
		float E = (int) (365.25f * (Y + 4716));
		float F = (int) (30.6001f * (M + 1));
		float JD = (C + D + E + F) - 1524.5f;
		return JD;
	}

	/**
	 * 根据出生日期获得年龄
	 * 
	 * @param birthday
	 * @return int
	 */
	public static int getAge(Date birthday) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(birthday);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		int age = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		return age < 0 ? 0 : age;
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            ,day
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int day) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.setTimeInMillis(cld.getTimeInMillis() + ((long) day) * 24 * 3600
				* 1000);
		return cld.getTime();
	}

	/**
	 * 
	 * 使用预设格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * 
	 * @return
	 */

	public static Date parse(String strDate) {

		return parse(strDate, DATE_FULL_STR);

	}

	/**
	 * 
	 * 使用用户格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * 
	 * @param pattern
	 *            日期格式
	 * 
	 * @return
	 */

	public static Date parse(String strDate, String pattern) {

		SimpleDateFormat df = new SimpleDateFormat(pattern);

		try {

			return df.parse(strDate);

		} catch (ParseException e) {

			e.printStackTrace();

			return null;

		}

	}

	/**
	 * 
	 * 两个时间比较
	 * 
	 * @param date
	 * 
	 * @return
	 */

	public static int compareDateWithNow(Date date1) {

		Date date2 = new Date();

		int rnum = date1.compareTo(date2);

		return rnum;

	}

	/**
	 * 
	 * 两个时间比较(时间戳比较)
	 * 
	 * @param date
	 * 
	 * @return
	 */

	public static int compareDateWithNow(long date1) {

		long date2 = dateToUnixTimestamp();

		if (date1 > date2) {

			return 1;

		} else if (date1 < date2) {

			return -1;

		} else {

			return 0;

		}

	}

	/**
	 * 
	 * 获取系统当前时间
	 * 
	 * @return
	 */

	public static String getNowTime() {

		SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);

		return df.format(new Date());

	}

	/**
	 * 
	 * 获取系统当前时间
	 * 
	 * @return
	 */

	public static String getNowTime(String type) {

		SimpleDateFormat df = new SimpleDateFormat(type);

		return df.format(new Date());

	}

	/**
	 * 
	 * 获取系统当前计费期
	 * 
	 * @return
	 */

	public static String getJFPTime() {

		SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);

		return df.format(new Date());

	}

	/**
	 * 
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param String
	 *            date 需要转换的日期 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return long 时间戳
	 */

	public static long dateToUnixTimestamp(String date) {

		long timestamp = 0;

		try {

			timestamp = new SimpleDateFormat(DATE_FULL_STR).parse(date)
					.getTime();

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return timestamp;

	}

	/**
	 * 
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param String
	 *            date 需要转换的日期 yyyy-MM-dd
	 * 
	 * @return long 时间戳
	 */

	public static long dateToUnixTimestamp(String date, String dateFormat) {

		long timestamp = 0;

		try {

			timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();

		} catch (ParseException e) {

			e.printStackTrace();

		}

		return timestamp;

	}

	/**
	 * 
	 * 将当前日期转换成Unix时间戳
	 * 
	 * @return long 时间戳
	 */

	public static long dateToUnixTimestamp() {

		long timestamp = new Date().getTime();

		return timestamp;

	}

	/**
	 * 
	 * 将Unix时间戳转换成日期
	 * 
	 * @param long timestamp 时间戳
	 * 
	 * @return String 日期字符串
	 */

	public static String unixTimestampToDate(long timestamp) {

		SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);

		sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));

		return sd.format(new Date(timestamp));

	}
	/**
	 * 
	 * 将当前时间按照指定格式转换
	 * 
	 * @param format
	 *            日期格式
	 * @return 已经格式化的日期字符串
	 */
	public static String formatDate(String format) {
		return formatDate(new java.util.Date(), format);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return 已经格式化的日期字符串
	 * @author 罗家友
	 */
	public static String formatDate(Date date, String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				format);
		return formatter.format(date);
	}
	/**
	 * yyyyMMddHHmmss  to yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String changeTime(String time){
		try{
	        SimpleDateFormat oldFormat  = new SimpleDateFormat("yyyyMMddHHmmss");
	        SimpleDateFormat newFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date date = oldFormat.parse(time);
	        return newFormat.format(date);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
    }
}
