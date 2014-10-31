package com.nationsky.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

public class FileUtils {

	public static void copyInputStreamToFile(InputStream source, File destination) {
		try {
			org.apache.commons.io.FileUtils.copyInputStreamToFile(source, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("拷贝文件出错FileUtils.copyInputStreamToFile");
		}

	}

	public static String readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		String text = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				text += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return text;
	}

	public static String readFileByByte(String fileName) {
		StringBuffer text = new StringBuffer();
		try {
			// 创建文件输入流对象
			InputStream is = new FileInputStream(fileName);
			// 设定读取的字节数
			int n = 512;
			byte buffer[] = new byte[n];
			// 读取输入流
			while ((is.read(buffer, 0, n) != -1) && (n > 0)) {
				text.append(new String(buffer));
			}
			// 关闭输入流
			is.close();
		} catch (IOException ioe) {
			System.out.println(ioe);
		} catch (Exception e) {
			System.out.println(e);
		}
		return text.toString();
	}

	public static void copyFile(String filePath, String filePathTo) throws IOException {
		File file1 = new File(filePath);
		File file2 = new File(filePathTo);
		copyFile(file1, file2);
	}

	public static boolean copyFile(File file, File fileTo) throws IOException {
		if (file.exists()) {
			int length = 2097152;
			FileInputStream in = new FileInputStream(file);
			FileOutputStream out = new FileOutputStream(fileTo);
			FileChannel inC = in.getChannel();
			FileChannel outC = out.getChannel();
			while (true) {
				if (inC.position() == inC.size()) {
					inC.close();
					outC.close();
					return true;
				}
				if ((inC.size() - inC.position()) < 20971520) {
					length = (int) (inC.size() - inC.position());
				} else {
					length = 20971520;
				}
				inC.transferTo(inC.position(), length, outC);
				inC.position(inC.position() + length);
			}
		} else {
			System.out.println("文件不存在");
			return false;
		}
	}

	public static void main(String[] args) {
//		File f1 = new File("F:\\pns1442.zip");
//		File f2 = new File("C:\\SDK\\config\\pns1442.zip");
//		try {
//			copyFile(f1, f2);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
