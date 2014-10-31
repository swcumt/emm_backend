package com.nationsky.webapp.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

public class HttpPost {
	
    	/**
	     * 上传图片
	     * @param urlStr
	     * @param textMap
	     * @param fileMap
	     * @return
	     */ 
	    @SuppressWarnings("rawtypes")
		public static String formUpload(String urlStr, Map<String, String> textMap,Map<String, String> fileMap) { 
	        String res = ""; 
	        HttpURLConnection conn = null; 
	        String BOUNDARY = Tools.getuuid(); //boundary就是request头和上传文件内容的分隔符 
	        try { 
	            URL url = new URL(urlStr); 
	            conn = (HttpURLConnection) url.openConnection(); 
	            conn.setConnectTimeout(5000); 
	            conn.setReadTimeout(30000); 
	            conn.setDoOutput(true); 
	            conn.setDoInput(true); 
	            conn.setUseCaches(false); 
	            conn.setRequestMethod("POST"); 
	            conn.setRequestProperty("Connection", "Keep-Alive"); 
	            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)"); 
	            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY); 
	   
	            OutputStream out = new DataOutputStream(conn.getOutputStream()); 
	            // text 
	            if (textMap != null) { 
	                StringBuffer strBuf = new StringBuffer(); 
	                Iterator iter = textMap.entrySet().iterator(); 
	                while (iter.hasNext()) { 
	                    Map.Entry entry = (Map.Entry) iter.next(); 
	                    String inputName = (String) entry.getKey(); 
	                    String inputValue = (String) entry.getValue(); 
	                    if (inputValue == null) { 
	                        continue; 
	                    } 
	                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n"); 
	                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n"); 
	                    strBuf.append(inputValue); 
	                } 
	                out.write(strBuf.toString().getBytes()); 
	            } 
	   
	            // file 
	            if (fileMap != null) { 
	                Iterator iter = fileMap.entrySet().iterator(); 
	                while (iter.hasNext()) { 
	                    Map.Entry entry = (Map.Entry) iter.next(); 
	                    String inputName = (String) entry.getKey(); 
	                    String inputValue = (String) entry.getValue(); 
	                    if (inputValue == null) { 
	                        continue; 
	                    } 
	                    File file = new File(inputValue); 
	                    String filename = file.getName(); 
	                    String contentType = new MimetypesFileTypeMap().getContentType(file); 
	                    if (filename.endsWith(".png")) { 
	                        contentType = "image/png"; 
	                    } 
	                    if (contentType == null || contentType.equals("")) { 
	                        contentType = "application/octet-stream"; 
	                    } 
	   
	                    StringBuffer strBuf = new StringBuffer(); 
	                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n"); 
	                    strBuf.append("Content-Disposition: form-data; name=\""+ inputName + "\"; filename=\"" + filename+ "\"\r\n"); 
	                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n"); 
	                    out.write(strBuf.toString().getBytes()); 
	                    DataInputStream in = new DataInputStream(new FileInputStream(file)); 
	                    int bytes = 0; 
	                    byte[] bufferOut = new byte[1024]; 
	                    while ((bytes = in.read(bufferOut)) != -1) { 
	                        out.write(bufferOut, 0, bytes); 
	                    } 
	                    in.close(); 
	                } 
	            } 
	   
	            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(); 
	            out.write(endData); 
	            out.flush(); 
	            out.close(); 
	   
	            // 读取返回数据 
	            StringBuffer strBuf = new StringBuffer(); 
	            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8")); 
	            String line = null; 
	            while ((line = reader.readLine()) != null) { 
	                strBuf.append(line).append("\n"); 
	            } 
	            res = strBuf.toString(); 
	            reader.close(); 
	            reader = null; 
	        } catch (Exception e) { 
	            System.out.println("发送POST请求出错。" + urlStr); 
	            e.printStackTrace(); 
	        } finally { 
	            if (conn != null) { 
	                conn.disconnect(); 
	                conn = null; 
	            } 
	        } 
	        return res; 
	}
	    
	    /**
	     * 向指定URL发送GET方法的请求
	     * 
	     * @param url
	     *            发送请求的URL
	     * @param param
	     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
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
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 建立实际的连接
	            connection.connect();
	            // 获取所有响应头字段
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
	            for (String key : map.keySet()) {
	                System.out.println(key + "--->" + map.get(key));
	            }
	            // 定义 BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
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
	     * 
	     * @param urlStr
	     * @param textMap
	     * @param fileMap
	     * @return
	     */
	    public static String fromJquery(String urlStr, String json) { 
	        String res = ""; 
	        HttpURLConnection conn = null; 
	        try { 
	            URL url = new URL(urlStr);
	            conn = (HttpURLConnection) url.openConnection();
	            conn.setConnectTimeout(5000); 
	            conn.setReadTimeout(30000); 
	            conn.setDoOutput(true); 
	            conn.setDoInput(true); 
	            conn.setUseCaches(false); 
	           
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            conn.setRequestMethod("POST");
	            PrintWriter out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));  
	            // text 
	            if (json != null) {
	                out.print(json); 
	            } 
	            out.flush(); 
	            out.close(); 
	            // 读取返回数据 
	            StringBuffer strBuf = new StringBuffer(); 
	            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8")); 
	            String line = null; 
	            while ((line = reader.readLine()) != null) { 
	                strBuf.append(line).append("\n"); 
	            } 
	            res = strBuf.toString(); 
	            reader.close(); 
	            reader = null; 
	        } catch (Exception e) { 
	            System.out.println("发送POST请求出错。" + urlStr); 
	            e.printStackTrace(); 
	        } finally { 
	            if (conn != null) { 
	                conn.disconnect(); 
	                conn = null; 
	            } 
	        } 
	        return res; 
	}
	    
	    
	    
	    
	    public static void main(String[] args){
	    	String json = "{\"nickname\":\"中文\"}";
			//HttpPost.fromJquery("http://192.168.2.219:80/appService/user",json);
	    	HttpPost.sendGet("http://192.168.2.219:80/appService/user",json);
	    	//WeixinUtil.httpRequest("http://192.168.2.219:80/appService/user","POST",json);
	    }
}
