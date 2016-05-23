package com.will.framework.util.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/** 
* @ClassName: GenerateLotteryUtil 
* @Description: 抽奖劵号和期数生产工具
* @author will
* @date 2014年12月9日 上午9:36:11 
*  
*/ 
public abstract class GenerateLotteryUtil {
	/** 
	* @Title: ticket 
	* @Description: 生成抽奖卷号
	* @param @param prefix 抽奖类型
	* @param @return    抽奖卷号 
	* @return String    返回类型 
	* @throws 
	*/
	public static String ticket(String prefix) {
		Calendar now = Calendar.getInstance();
		StringBuffer ticketNo = new StringBuffer(prefix);
		// 2013-12-11 16:24:41 131624121141132145
		ticketNo.append(new SimpleDateFormat("yyyyMMmmddHHSSS").format(now.getTime()));
		// 三位随机数
		int ran = (int) (Math.random() * 900) + 100;
		ticketNo.append(ran);
		return ticketNo.toString();
	}
	
	/** 
	* @Title: nextPeriod 
	* @Description: 生成下一期抽奖期数
	* @param @param lotteryType 抽奖类型
	* @param @return    下一期抽奖期数
	* @return String    返回类型 
	* @throws 
	*/
	public static String nextPeriod(int lotteryType) {
		Calendar now = Calendar.getInstance();
		switch (lotteryType) {
		case 1:
			// 201312111期(日抽奖)
			return new SimpleDateFormat("yyyyMMdd").format(now.getTime()) + "1";
		case 2:
			// 2013122期(月抽奖)
			return new SimpleDateFormat("yyyyMM").format(now.getTime()) + "2";
		case 3:
			// 2013043期(季抽奖)
			int quarter = (now.get(Calendar.MONTH) + 1);
			quarter = (quarter % 3 == 0) ? quarter / 3 : quarter / 3 + 1;
			return now.get(Calendar.YEAR) + "0" + quarter + "3";
		case 4:
			// 20134期(年抽奖)
			return now.get(Calendar.YEAR) + "4";
		default:
			break;
		}
		return null;
	}
	
	/** 
	* @Title: prePeriod 
	* @Description: 生成上一期抽奖期数
	* @param @param lotteryType 抽奖类型
	* @param @return    上一期抽奖期数 
	* @return String    返回类型 
	* @throws 
	*/
	public static String prePeriod(int lotteryType) {
		Calendar now = Calendar.getInstance();
		switch (lotteryType) {
		case 1:
			// 201312111期(日抽奖)
			now.add(Calendar.DAY_OF_MONTH, -1);// 前一天时间
			return new SimpleDateFormat("yyyyMMdd").format(now.getTime()) + "1";
		case 2:
			// 2013122期(月抽奖)
			now.add(Calendar.MONTH, -1);// 前一月时间
			return new SimpleDateFormat("yyyyMM").format(now.getTime()) + "2";
		case 3:
			// 2013043期(季抽奖)
			int quarter = (now.get(Calendar.MONTH) + 1);
			quarter = (quarter % 3 == 0) ? quarter / 3 : quarter / 3 + 1;
			if (quarter == 1) {
				now.add(Calendar.YEAR, -1);
				return now.get(Calendar.YEAR) + "043";
			} else {
				return now.get(Calendar.YEAR) + "0" + (quarter - 1) + "3";
			}
		case 4:
			// 20134期(年抽奖)
			now.add(Calendar.YEAR, -1);// 前一年时间
			return now.get(Calendar.YEAR) + "4";
		default:
			return null;
		}
	}
	
	public static void main(String args[]){
		System.out.println(prePeriod(1));
		System.out.println(prePeriod(2));
		System.out.println(prePeriod(3));
		System.out.println(prePeriod(4));
	}
}