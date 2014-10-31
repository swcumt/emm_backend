package com.nationsky.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Http {

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url 发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送文件
	 * @param httpUrl HTTP地址
	 * @param paramMap 参数值可选(File/String/Integer)
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String sendPost(String httpUrl, Map<String, Object> paramMap) throws ParseException, IOException {
		HttpPost httpPost = new HttpPost(httpUrl);
		httpPost.setHeader("User-Agent", "SOHUWapRebot");
		httpPost.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpPost.setHeader("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.7");
		httpPost.setHeader("Connection", "keep-alive");
		MultipartEntity mutiEntity = new MultipartEntity();

		//添加参数
		if (paramMap != null) {
			Set<String> keys = paramMap.keySet();
			String keyName = "";
			if (!keys.isEmpty()) {
				Iterator<String> iterator = keys.iterator();
				ContentBody contentBody = null;
				while (iterator.hasNext()) {
					keyName = iterator.next();
					Object obj = paramMap.get(keyName);
					if (obj instanceof File) {
						contentBody = new FileBody((File) obj);
					} else if (obj instanceof String) {
						contentBody = new StringBody(String.valueOf(obj), Charset.forName("utf-8"));
					} else if (obj instanceof Integer) {
						contentBody = new StringBody(String.valueOf(obj), Charset.forName("utf-8"));
					} else if (obj instanceof Long) {
						contentBody = new StringBody(String.valueOf(obj), Charset.forName("utf-8"));
					} else {
						contentBody = new StringBody("", Charset.forName("utf-8"));
					}
					mutiEntity.addPart(keyName, contentBody);
				}
			}
		}
		httpPost.setEntity(mutiEntity);
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse httpResponse = httpclient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		String content = EntityUtils.toString(httpEntity);
		return content;
	}

	public static void main(String[] args) {
		String httpUrl = "http://172.27.35.11:3000";
		String filePath = "c://push.zip";
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("serviceId", "111");
			paramMap.put("sdkZip", new File(filePath));
			Http.sendPost(httpUrl, paramMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
