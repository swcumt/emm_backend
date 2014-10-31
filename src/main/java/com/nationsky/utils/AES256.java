package com.nationsky.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Encoder;

public class AES256 {

	/**
	 * AES256加密
	 * @param str 需要加密的字符串
	 * @param secret 加密使用的密钥
	 * @return 加密之后的结果
	 * @throws Exception
	 */
	public static String encrypt(String str, String secret) throws Exception {
//		Key k = toKey(Base64.decodeBase64(secret));
		Key k = toKey(secret.getBytes());
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return new BASE64Encoder().encode(cipher.doFinal(str.getBytes("UTF-8")));
	}

	/**
	 * AES256解密
	 * @param encryptedData 需要解密的字符串
	 * @param secret 解密使用的密钥
	 * @return 解密出来的字符串
	 * @throws Exception
	 */
	public static String decrypt(String encryptedData, String secret) throws Exception {
//		Key k = toKey(Base64.decodeBase64(secret));
		Key k = toKey(secret.getBytes());
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
		cipher.init(Cipher.DECRYPT_MODE, k);
		return new String(cipher.doFinal(Base64.decodeBase64(encryptedData)));
	}

	/**
	 * 将byte[]转换成Key
	 * @param key byte[]的key
	 * @return Key
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		return new SecretKeySpec(key, "AES");
	}

	/**
	 * 获取一个密钥.经过Base64进行转换之后的密钥.
	 * @return 经过Base64之后的密钥
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static String getkey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(256);
		SecretKey secretKey = kg.generateKey();
		return Base64.encodeBase64String(secretKey.getEncoded());
	}

	// public static void main(String[] args) throws Exception {
	//
	// System.out.println(getkey());
	//
	// String source = "aes";
	//
	// System.out.println("原文：" + source);
	//
	// String key = "884b2fbc1397c37a4f6fe951aa19679d";
	// System.out.println("密钥：" + key);
	//
	// String encryptData = encrypt(source, key);
	// System.out.println("加密：" + encryptData);
	//
	// encryptData = "W4h2mHPKMq8ckjUsS2B5KQ==";
	// String decryptData = decrypt(encryptData, key);
	// System.out.println("解密: " + decryptData);
	// }
	public static void main(String[] args) {
		try {
			System.out.println(encrypt("HelloWorld", "11111111111111111111111111111111"));

			System.out.println(decrypt("4A+9BRhbcaZr0h8y0pZ7LA==", "884b2fbc1397c37a4f6fe951aa19679d"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
