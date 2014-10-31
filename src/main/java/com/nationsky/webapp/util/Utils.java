package com.nationsky.webapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import com.nationsky.model.Users;

public class Utils {

	/**
	 * 根据请求Request中的Token获取用户信息.
	 * @param request 当前请求对象
	 * @return Users
	 */
	public static Users getUser(HttpServletRequest request) {
		String token = request.getHeader("TOKENID");
		if (request == null || token == null || token.equals("")) {
			return null;
		}
		ServletContext context = request.getSession().getServletContext();
		Map<String, Users> userMap = (Map<String, Users>) context.getAttribute("USERMAP");
		if (userMap != null || userMap.size() > 0) {
			return userMap.get(token);
		}
		return null;
	}

	/**
	 * 获取文件后缀名
	 * @param fileName 文件名称
	 * @return 后缀名(.ZIP/.PNG) 大写
	 */
	public static String getExtName(String fileName) {
		String prefix = "";
		if (fileName != null && fileName.length() > 0 && fileName.contains(".")) {
			prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
			prefix = prefix.toUpperCase();
		}
		return prefix;
	}

	/**
	 * 获取Tomcat的WebApps路径
	 * @param request HttpServletRequest
	 * @return Tomcat的WebApps路径
	 */
	public static String getTomcatWebappsPath(HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		String projectPath = servletContext.getRealPath("/");
		String projectName = servletContext.getContextPath();
		projectName = projectName.substring(1, projectName.length());
		String tomcatWebappsPath = projectPath.substring(0, projectPath.indexOf(projectName));
		return tomcatWebappsPath;
	}

	/**
	 * 文件大小转换</br>
	 * 
	 * @param size
	 * @return
	 */
	public static String formetFileSize(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String sizeStr = "";
		if (size < 1024) {
			sizeStr = df.format((double) size) + "B";
		} else if (size < 1048576) {
			sizeStr = df.format((double) size / 1024) + "K";
		} else if (size < 1073741824) {
			sizeStr = df.format((double) size / 1048576) + "M";
		} else {
			sizeStr = df.format((double) size / 1073741824) + "G";
		}
		return sizeStr;
	}

	public static void main(String[] args) {
		System.out.println(Utils.formetFileSize(1024L));
	}

	/**
	 * 初始化参数
	 * 
	 * @param key constant.properties中的Key
	 * @return constant.properties中的Key对应的值
	 */
	public static String getPropertiesValue(String key) {
		Properties prop = new Properties();
		InputStream in = Utils.class.getResourceAsStream("/constant.properties");
		try {
			prop.load(in);
			return prop.getProperty(key, "");
		} catch (IOException e) {
			System.err.println("读取constant.properties错误!!!");
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "").replaceAll("-", "");
	}

	/**
	 * 获取当前时间(毫秒)
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 获取当前时间(自定义格式)
	 * 
	 * @return 自定义格式
	 */
	public static String getCurrentTime(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	public static Date getFormatDate(String date, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}

	/**
	 * 解码
	 * 
	 * @param str 需要解码的字符串
	 * @return
	 */
	public static String decode(String str) {
		String newstr = null;
		try {
			if (str != null) {
				newstr = URLDecoder.decode(str, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newstr;
	}

	/**
	 * 判断是不是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().equals(""))
			return true;
		return false;
	}

	/**
	 * 判断日期格式是否正确
	 * 
	 * @param date 日期
	 * @param format 格式
	 * @return
	 */
	public static boolean isDate(String date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date d = null;
		try {
			d = df.parse(date);
		} catch (Exception e) {
			// 如果不能转换,肯定是错误格式
			return false;
		}
		String s1 = df.format(d);
		return date.equals(s1);
	}

	/**
	 * 判断email是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (null == email || "".equals(email))
			return false;
		// Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 判断手机号格式是否正确
	 * 
	 * @param phone 手机号码
	 * @return
	 */
	public static boolean isPhone(String phone) {
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(phone);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * MD5加密
	 * 
	 * @param share_key 需要加密的字符串
	 * @return MD5加密后的字符串
	 */
	public static String getMD5(String share_key) {
		byte[] source = share_key.getBytes();
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}

		return s;
	}

	/**
	 * 判断字符串和MD5加密后的是否一致
	 * 
	 * @param value MD5加密后的字符串
	 * @param share_key 原字符串
	 * @return
	 */
	public static boolean checkMD5(String value, String share_key) {
		int length = value.length();
		if (length < 32)
			return false;
		String md5 = value.substring(length - 32);
		String str = value.substring(0, length - 32);
		String md5_str = getMD5((str + share_key));
		if (md5_str.equals(md5))
			return true;
		else
			return false;
	}

	public static String encode2Unicode(String str) {
		return Base64.encodeBase64String(StringUtils.getBytesUtf8(str));
	}

	/**
	 * utf-8 转unicode
	 * 
	 * @param inStr
	 * @return String
	 */
	public static String utf8ToUnicode(String inStr) {

		if (null == inStr) {
			return null;
		}
		char[] myBuffer = inStr.toCharArray();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inStr.length(); i++) {
			UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
			if (ub == UnicodeBlock.BASIC_LATIN) {
				sb.append(myBuffer[i]);
			} else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				int j = (int) myBuffer[i] - 65248;
				sb.append((char) j);
			} else {
				int s = (int) myBuffer[i];
				String hexS = Integer.toHexString(s);
				String unicode = "\\u" + hexS;
				sb.append(unicode.toLowerCase());
			}
		}
		return sb.toString();
	}

	public String gbk2utf8(String gbk) {
		String l_temp = GBK2Unicode(gbk);
		l_temp = unicodeToUtf8(l_temp);
		return l_temp;
	}

	public String utf82gbk(String utf) {
		String l_temp = utf8ToUnicode(utf);
		l_temp = Unicode2GBK(l_temp);
		return l_temp;
	}

	public static boolean isNeedConvert(char para) {
		return ((para & (0x00FF)) != para);
	}

	/**
	 * 
	 * @param theString
	 * @return String
	 */
	public static String unicodeToUtf8(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	/**
	 * 
	 * @param str
	 * @return String
	 */

	public static String GBK2Unicode(String str) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char chr1 = (char) str.charAt(i);

			if (!isNeedConvert(chr1)) {
				result.append(chr1);
				continue;
			}

			result.append("\\u" + Integer.toHexString((int) chr1));
		}

		return result.toString();
	}

	/**
	 * 
	 * @param dataStr
	 * @return String
	 */

	public static String Unicode2GBK(String dataStr) {
		int index = 0;
		StringBuffer buffer = new StringBuffer();

		int li_len = dataStr.length();
		while (index < li_len) {
			if (index >= li_len - 1 || !"\\u".equals(dataStr.substring(index, index + 2))) {
				buffer.append(dataStr.charAt(index));

				index++;
				continue;
			}

			String charStr = "";
			charStr = dataStr.substring(index + 2, index + 6);

			char letter = (char) Integer.parseInt(charStr, 16);

			buffer.append(letter);
			index += 6;
		}

		return buffer.toString();
	}

	public static byte[] getSHA1(byte[] bytes) {
		byte[] _bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(bytes);
			_bytes = md.digest();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return _bytes;
	}

	public static long getUnixTimestamp(int sep) {
		Calendar c = Calendar.getInstance();
		GregorianCalendar g = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		g.add(Calendar.DATE, sep);
		Date date = g.getTime();
		return date.getTime() / 1000L;
	}

	public static String bytetoString(byte[] digest) {
		String str = "";
		String tempStr = "";

		for (int i = 1; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}
		return str.toLowerCase();
	}

	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String inputStream2String(InputStream is) {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static boolean isValid(String str) {
		return null != str && !str.isEmpty() ? true : false;
	}

	public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = lat1 * Math.PI / 180.0;
		double radLat2 = lat2 * Math.PI / 180.0;
		double a = radLat1 - radLat2;
		double b = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

}
