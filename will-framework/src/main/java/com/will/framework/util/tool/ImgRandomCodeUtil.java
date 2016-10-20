package com.will.framework.util.tool;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImgRandomCodeUtil {
	private static final String[] chars = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private static final char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };

	private static final String[] cals = { "+", "*" };// {"+", "-", "*", "/",
														// "%"};
	private static final int SIZE = 4;// 显示字符个数
	private static final int NUM = 2;// 显示字符中的数字个数
	private static final int NUM1 = 1;// 显示字符中的运算符个数
	private static final int LINES = 10;// 干扰线数量
	private static final int WIDTH = 160;// 图片宽度
	private static final int HEIGHT = 40;// 图片高度
	private static final int FONT_SIZE = 40;// 字体大小

	public static Map<String, BufferedImage> createCodeImage() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics graphic = image.getGraphics();
		graphic.setColor(Color.LIGHT_GRAY);
		graphic.fillRect(0, 0, WIDTH, HEIGHT);
		Random ran = new Random();

		StringBuilder sb = new StringBuilder();
		// 画随机字符

		for (int i = 0; i < SIZE; i++) {
			int r = ran.nextInt(codeSequence.length);
			String code = String.valueOf(codeSequence[r]);
			sb.append(code);
			graphic.setColor(Color.BLACK);
			graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
			graphic.drawString(code, (i + 1) * (WIDTH / (SIZE + 1)), HEIGHT - 4);
		}

		// 画干扰线
		for (int i = 1; i <= LINES; i++) {
			graphic.setColor(getRandomColor());
			graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
		}

		Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
		// 结果和图片成键值对
		map.put(sb.toString(), image);
		return map;
	}

	public static Map<String, BufferedImage> createMathImage() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics graphic = image.getGraphics();
		graphic.setColor(Color.LIGHT_GRAY);
		graphic.fillRect(0, 0, WIDTH, HEIGHT);
		Random ran = new Random();
		// 记录字符中的数字
		int[] num = new int[NUM];
		int index = 0;
		// 记录运算符号
		String[] cals1 = new String[NUM1];
		int index1 = 0;
		StringBuilder sb = new StringBuilder();
		// 画随机字符

		for (int i = 1; i < SIZE; i++) {
			// 数字
			if (i % 2 == 1) {
				int r = ran.nextInt(chars.length);
				graphic.setColor(Color.BLACK);
				graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
				graphic.drawString(chars[r], (i - 1) * WIDTH / SIZE, 7 * HEIGHT / 8);
				num[index++] = Integer.parseInt(chars[r]);

			} else {
				// 画符号
				int r = ran.nextInt(cals.length);
				graphic.setColor(new Color(0, 0, 0));
				graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
				graphic.drawString(cals[r], (i - 1) * WIDTH / SIZE, 7 * HEIGHT / 8);
				cals1[index1++] = cals[r];
			}
			if (i == SIZE - 1) {
				graphic.setColor(new Color(0, 0, 0));
				graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
				graphic.drawString("=", i * WIDTH / SIZE, 7 * HEIGHT / 8);
			}
		}
		// 计算字符运算结果
		int result = cal(num, cals1);
		sb.append(String.valueOf(result));

		// 画干扰线
		for (int i = 1; i <= LINES; i++) {
			graphic.setColor(getRandomColor());
			graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
		}

		Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
		// 结果和图片成键值对
		map.put(sb.toString(), image);
		return map;
	}

	public static Map<String, BufferedImage> createMixImage() {
		Long ron = Math.round(Math.random() * 10) % 2;
		if (ron > 0) {
			return createMathImage();
		} else {
			return createCodeImage();
		}
	}

	public static Map<String, BufferedImage> createImage() {
		return createMixImage();
	}

	private static int cal(int[] num, String[] cals1) {
		int result = 0;
		if ("+".equals(cals1[0])) {
			result = num[0] + num[1];
		} else if ("-".equals(cals1[0])) {
			result = num[0] - num[1];
		} else if ("*".equals(cals1[0])) {
			result = num[0] * num[1];
		} else if ("/".equals(cals1[0])) {
			result = num[0] / num[1];
		} else if ("%".equals(cals1[0])) {
			result = num[0] % num[1];
		}
		return result;
	}

	public static InputStream change(BufferedImage image) throws Exception {
		// 将image图片压缩成JPEG
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		JPEGImageEncoder encode = JPEGCodec.createJPEGEncoder(bos);
		encode.encode(image);
		// 将bos中存储的JPEG格式图片字节取出
		byte[] bytes = bos.toByteArray();
		return new ByteArrayInputStream(bytes);
	}

	private static Color getRandomColor() {
		Random ran = new Random();
		Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
		return color;
	}

}
