package com.will.framework.util.code;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Aes加密、解密工具类
 * @author will
 */
public class AesEncryptUtil {
	// 默认秘钥
	private static final String key = "123qwertyuiop678";
	// 算法
	private static final String algorithm = "AES";
	// 字符名称
	private static final String charsetName = "UTF-8";

	/**
	 * 解密
	 * 
	 * @param plainData
	 *            待解密内容
	 * @param key
	 *            解密密钥
	 * @return 已经解密内容,即明码
	 */
	public static String decrypt(String plainData, String key) throws Exception {
		if (null == key) {
			throw new Exception("key is null");
		}
		if (null==plainData) {
			throw new Exception("plainData is null");
		}
		byte[] encontent = decryptor(parseHexStr2Byte(plainData), key);
		return new String(encontent, charsetName);
	}

	/**
	 * 解密
	 * 
	 * @param plainData
	 *            待解密内容
	 * @return 已经解密内容,即明码
	 * @throws Exception
	 */
	public static String decrypt(String plainData) throws Exception {
		return decrypt(plainData,key);
	}

	/**
	 * 加密
	 * 
	 * @param plainData
	 *            需要加密的内容
	 * @param key
	 *            加密密码
	 * @return 已经加密内容
	 */
	public static String encrypt(String plainData, String key) throws Exception {
		if (null == key) {
			throw new Exception("key is null");
		}
		if (null == plainData) {
			throw new Exception("plainData is null");
		}
		byte[] encontent = encryptByte(plainData, key);
		return parseByte2HexStr(encontent);
	}

	/**
	 * 加密
	 * 
	 * @param plainData
	 *            需要加密的内容
	 * @return 已经加密内容
	 * @throws Exception
	 */
	public static String encrypt(String plainData) throws Exception {
		return encrypt(plainData,key);
	}

	private static byte[] encryptByte(String content, String password)
			throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance(algorithm);
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(password.getBytes(charsetName));
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);// 创建密码器
		byte[] byteContent = content.getBytes(charsetName);
		cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(byteContent);
		return result;
	}

	private static byte[] decryptor(byte[] content, String password)
			throws Exception {
		if (password == null || "".equals(password))
			password = key;
		KeyGenerator kgen = KeyGenerator.getInstance(algorithm);
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(password.getBytes(charsetName));
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm);// 创建密码器
		cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
		byte[] result = cipher.doFinal(content);
		return result; //
	}

	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return byte[]
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	private AesEncryptUtil(){}
	
	public static void main(String[] str) throws Exception {
		System.out.println(encrypt("luojiayou"));
		System.out.println(decrypt("4DE1E88EE4E7F4524E670B5DAA805206"));
	}
}