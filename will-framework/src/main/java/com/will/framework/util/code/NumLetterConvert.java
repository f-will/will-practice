package com.will.framework.util.code;

/**
 * 字母数字相互转换<br/>
 * 用户推广
 * @author will
 */
public class NumLetterConvert {

	/**
	 * 将字母转换成数字_1  
	 * @param ALotter 
	 * @return 数字
	 */
    public static String letterToNum(String ALotter) {  
    	if(null == ALotter || "".equals(ALotter)) return null;
        String reg = "[a-zA-Z]";  
        StringBuffer strBuf = new StringBuffer();  
        ALotter = ALotter.toLowerCase();  
        if (null != ALotter && !"".equals(ALotter)) {  
            for (char c : ALotter.toCharArray()) {  
                if (String.valueOf(c).matches(reg)) { 
                	if(String.valueOf(c).equals("z")){
                		strBuf.append("0"); 
                	}else{
                		strBuf.append(c - 96);  
                	}                    
                } else {  
                    strBuf.append(c);  
                }  
            }  
            return strBuf.toString();  
        } else {  
            return ALotter;  
        }  
    }  
  
    /**
     * 将数字转换成字母 <br/>
     * 用途:用于用户登陆设置推广
     * @param ANum 数字
     * @return
     */
    public static String numToLetter(String ANum) { 
    	if(null == ANum || "".equals(ANum)) return null;
    	String s = "";
    	ANum.getBytes();
        for (byte b : ANum.getBytes()) { 
        	b = 48==b?74:b;
            s += (char) (b + 48);  
        }  
        return s;
    }  
  
    public static void main(String[] args) {  
        //String letter = "1";  
        String num = "101110";  
        String s = numToLetter(num);  
        System.out.println(s);  
        s =letterToNum(s);
        System.out.println(s);  
    }  

}
