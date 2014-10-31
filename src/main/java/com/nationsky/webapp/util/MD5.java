package com.nationsky.webapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	protected static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	protected static MessageDigest messagedigest = null;

	public static String md5(String string) {

		return md5(string, "UTF-8");
	}

	public static String md5(String string, String charset) {
		try {

			byte[] tmp = messagedigest.digest(string.getBytes(charset));

			StringBuilder buf = new StringBuilder(32);
			for (byte b : tmp) {
				buf.append(String.format("%02x", new Object[] { Integer
						.valueOf(b & 0xFF) }));
			}
			return buf.toString();
		} catch (Exception e) {
		}
		return null;
	}

	public static String getFileMD5String(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0L,
				file.length());
		messagedigest.update(byteBuffer);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte[] bytes) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte[] bytes, int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[((bt & 0xF0) >> 4)];
		char c1 = hexDigits[(bt & 0xF)];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static void main(String[] args) {
		System.out.println(md5("NQ_Nqsky1130^Sky"));
//		log.info(new StringBuilder().append("MD5(\"admin\"): ").append(
//				md5("admin")).toString());
//		log.info(new StringBuilder().append("MD5(\"admin123!@#\"): ").append(
//				md5("admin123!@#")).toString());
//		log.info(new StringBuilder().append("MD5(\"auditor123!@#\"): ").append(
//				md5("auditor123!@#")).toString());
//		log.info(new StringBuilder().append("MD5(\"NQ_auditor123!@#^Sky\"): ")
//				.append(md5("NQ_auditor123!@#^Sky")).toString());
//		log.info(new StringBuilder().append("MD5(\"123456\"): ").append(
//				md5("123456")).toString());
//		log.info(new StringBuilder().append("MD5(\"NQ_123456^Sky\"): ").append(
//				md5("NQ_123456^Sky")).toString());
//		log.info(new StringBuilder().append("MD5(\"NQ_Nqsky1130^Sky\"): ")
//				.append(md5("NQ_Nqsky1130^Sky")).toString());
//		try {
//			File big = new File(
//					"F:\\temp\\cert\\Certisign Autoridade Certificadora AC1S.cer");
//			String md5 = getFileMD5String(big);
//			log.info(new StringBuilder().append("********").append(md5)
//					.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
