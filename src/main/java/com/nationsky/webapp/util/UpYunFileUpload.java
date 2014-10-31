package com.nationsky.webapp.util;

import java.io.File;
import java.io.IOException;

/**
 * 文件类空间的demo
 */
public class UpYunFileUpload {

	// 运行前先设置好以下三个参数
	private static final String BUCKET_NAME = "shiren1118";
	private static final String USER_NAME = "i5ting";
	private static final String USER_PWD = "shiren1118";

	/** 根目录 */
	private static final String DIR_ROOT = "/appcenter/";
 
	private static UpYun upyun = null;
 

	public static void main(String[] args) throws Exception {

		// 初始化空间
		upyun = new UpYun(BUCKET_NAME, USER_NAME, USER_PWD);

		// ****** 可选设置 begin ******

		// 切换 API 接口的域名接入点，默认为自动识别接入点
		// upyun.setApiDomain(UpYun.ED_AUTO);

		// 设置连接超时时间，默认为30秒
		// upyun.setTimeout(60);

		// 设置是否开启debug模式，默认不开启
		upyun.setDebug(true);

		// ****** 可选设置 end ******

		// 1.创建目录，有两种形式
		testMkDir();

		
//		String test_file = System.getProperty("user.dir") + "/aaaa.pdf";
		String test_file = "d://AppManagment.plist";
		File txtFile = new File(test_file);

		if (!txtFile.isFile()) {
			System.out.println("本地待上传的测试文件不存在！");
		}
		
		// 2.上传文件，图片空间的文件上传请参考 PicBucketDemo.java
		upYunInit(test_file,"AppManagment.plist"); 
	}
	
	public static String upYunInit(String plistPath,String plistName) throws IOException{
		// 初始化空间
		upyun = new UpYun(BUCKET_NAME, USER_NAME, USER_PWD);

		// ****** 可选设置 begin ******

		// 切换 API 接口的域名接入点，默认为自动识别接入点
		// upyun.setApiDomain(UpYun.ED_AUTO);

		// 设置连接超时时间，默认为30秒
		// upyun.setTimeout(60);

		// 设置是否开启debug模式，默认不开启
		upyun.setDebug(true);

		// ****** 可选设置 end ******

		// 1.创建目录，有两种形式
		testMkDir();

		File plistFile = new File(plistPath);

		if (!plistFile.isFile()) {
			System.out.println("本地待上传的测试文件不存在！");
		}
		
		// 2.上传文件，图片空间的文件上传请参考 PicBucketDemo.java
		return uploadFile(plistPath,plistName); 
	}

	
	/**
	 * 上传
	 * @param localfile 本地文件
	 * @param filename  上传到服务器上的文件名称
	 * @throws IOException
	 */
	public static String uploadFile(String localfile,String filename) throws IOException {
		// 要传到upyun后的文件路径
		String filePath = DIR_ROOT + filename;

		/*
		 * 上传方法3：采用数据流模式上传文件（节省内存），可自动创建父级目录（最多10级）
		 */
		File file = new File(localfile);
		boolean result3 = upyun.writeFile(filePath, file, true);
		System.out.println("3.上传 " + filePath + isSuccess(result3));
		return filePath;
	}


	/**
	 * 创建目录
	 */
	public static void testMkDir() {

		// 方法1：创建一级目录
		String dir1 = "/appcenter/";

		boolean result1 = upyun.mkDir(dir1);
		System.out.println("创建目录：" + dir1 + isSuccess(result1));
	}

  
	private static String isSuccess(boolean result) {
		return result ? " 成功" : " 失败";
	}
}
