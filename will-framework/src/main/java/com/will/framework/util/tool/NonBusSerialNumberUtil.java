package com.will.framework.util.tool;

import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang.StringUtils;
import com.will.framework.util.date.DateUtil;

/**
 * 非重要业务流水号生成帮助类
 *   MMHHmmss(8)+字母(1)+序列(9) 16位
 * @author will
 *
 */
public class NonBusSerialNumberUtil {
   private static AtomicLong serial = new AtomicLong(1);
   private static final long maxLong = 999999999l; //序列增长最大值
   private static final long minLong = 1l; //序列最小值
   private static char letter  = getLetter();
    /**
     * 获取流水号
     * @return
     */
	private static long getSerial() {
		long sn = serial.getAndIncrement();
		if (sn + 1 > maxLong) {// 如果达到上限，则更新上限
			setLetter();  //更新字母
			serial.set(minLong);// 更新上限
			sn = serial.getAndIncrement();
		}
		return sn;
	}
	
	/**
	 * 获取流水号(日期+序列) 18位</br>
	 * HHmmssSS(8位)+字母(1位)+9位序列
	 * @return
	 */
	public static String getSerialByDate(){
		String time =  DateUtil.getNowTime("HHmmssSS");
		String serial = StringUtils.leftPad(String.valueOf(getSerial()), 9, "0");
		return time + letter + serial;
	}
	
	/**
	 * 获取字母
	 * @return
	 */
	private static char getLetter(){
		return (char)(Math.random()*26 + 'A');
	}
	
	/**
	 * 设置字母
	 */
	private static void setLetter(){
		letter = (char)(Math.random()*26 + 'A');
	}
	
}
