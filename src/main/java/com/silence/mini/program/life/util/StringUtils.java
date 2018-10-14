package com.silence.mini.program.life.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * 字符通用操作
 *
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	private static Random randGen = new Random();
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
			+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	private static char[] numbers = ("0123456789").toCharArray();

	/**
	 * 获取表标识字符串
	 * @return
	 */
	public static final String genrateKey() {
		return null;
	}

	/**
	 * 获得随机的字符串
	 *
	 * @param length
	 * @return
	 */
	public static String randomString(int length) {
		if (length < 1) {
			return null;
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer).toLowerCase();
	}

	public static String randomNum(int length) {
		if (length < 1) {
			return null;
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbers[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}

	/**
	 * <b>toInteger</b><br/>
	 * <p>数字字符串转换</p> 
	 * <b>date：</b>2014-11-19 上午11:12:27<br/> 
	 *
	 * @param str
	 * @return
	 *
	 * @exception
	 * @since  1.0
	 */
	public static Integer toInteger(String str){
		if(StringUtils.isBlank(str)){
			return null;
		}else{
			return Integer.parseInt(str);
		}
	}

	/**
	 * 将指定的字符串中的指定字符，替换为新字符
	 *
	 * @param str
	 *            指定串
	 * @param oldStr
	 *            老字符串
	 * @param newStr
	 *            替换的新字符串
	 * @return
	 */
	public static String replaceAll(String str, String oldStr, String newStr) {
		if (str.indexOf(oldStr) != -1) {
			str = str.replace(oldStr, newStr);
		}
		return str;
	}

	/**
	 * 获得指定内容中，有start开始，end结尾的内容列表
	 *
	 * @param content
	 *            内容
	 * @param start
	 *            开始内容
	 * @param end
	 *            截止内容
	 * @return 符合条件的内容列表
	 */
	public static List<String> getContentBetween(String content, String start,
												 String end) {
		int index = content.indexOf(start);
		List<String> results = new ArrayList<String>();
		while (index != -1) {
			int e = content.indexOf(end, index);
			if (e == -1) {
				break;
			}
			results.add(content.substring(index + start.length(), e));
			index = content.indexOf(start, e);
		}
		return results;
	}

	/**
	 * 根据指定的URL获得对应的内容
	 *
	 * @param url
	 *            指定的url
	 * @return 获得的内容
	 * @throws IOException
	 */
	public static String getContent(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String temp = null;
		StringBuilder content = new StringBuilder();
		while ((temp = reader.readLine()) != null) {
			content.append(temp);
		}
		return content.toString();
	}

	/**
	 * 截断字符串(按字节截取)
	 * @param s
	 * @param n
	 * @return
	 */
	public static String trimStringInLength(String s, int n) {
		if (n == 0) {
			return s;
		}
		if (s == null)
			return "";

		int index = 0; // 定义游标位置
		StringBuffer ss = new StringBuffer();
		for (int i = 0; i < n && index < s.length(); i++) {
			if (s.charAt(index) < 255 && s.charAt(index) > 0
					|| Character.isDigit(s.charAt(index))) {

				ss.append(s.charAt(index)); // 如果当前字符不输于字母或者是数字，则添加到结果。
				index++;
			} else {
				ss.append(s.charAt(index)); // 如果当前字符是汉字，则添加到结果中，游标向前移动一位。
				index++;
				i++;
			}
		}
		if (index < s.length()) {
			ss.append("...");
		}
		return ss.toString();

	}

	/**
	 * 截断字符串(按字符截取)
	 *
	 * @param str
	 *            被截取的字符
	 * @param toCount
	 *            截取的长度
	 * @param format
	 *            截取后添加的字符串,通常是"..."
	 * @return
	 */
	public static String trimStringInLength(String str, int toCount,
											String format) {
		if (str == null)
			return "";
		if (format == null)
			format = "";
		if (str.length() > toCount) {
			str = str.substring(0, toCount - 1) + format;
		}
		return str;
	}

	/**
	 *
	 * 去除所有html元素
	 *
	 *
	 */

	public static String dropHtml(String Source) {
		if (org.apache.commons.lang3.StringUtils.isBlank(Source)) {
			return "";
		}
		Source.trim();
		String excuteString = null;
		int debut;
		int fin;
		debut = Source.indexOf("<");
		fin = Source.indexOf(">");

		while ((debut >= 0) && (fin > 0)) {
			debut = Source.indexOf("<");
			fin = Source.indexOf(">");
			if (debut < 0) {
				break;
			}
			if (debut >= fin) {
				debut = 0;
			}
			excuteString = null;
			excuteString = Source.substring(0, debut);
			excuteString.trim();
			excuteString = excuteString + "" + Source.substring(fin + 1).trim();
			Source = excuteString;
		}
		Source = Source.replaceAll("&nbsp", "");
		return Source;

	}

	/**
	 * 去除所有的html标记
	 *
	 * @param input
	 * @return
	 */
	public static String dropHtmlTag(String input) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		return str;
	}

	/**
	 * 去除Script标记
	 *
	 * @param input
	 *            目标字符串
	 * @return
	 */
	public static String dropScriptTag(String input) {
		if (isBlank(input))
			return "";
		String rs = input.replaceAll("<script[^>]*?>.*?</script>", "");
		return rs;
	}

	/**
	 * <p>
	 * 检查字符串,字符串为空格,("") 或null
	 * </p>
	 *
	 * <pre>
	 * StringUtil.isBlank(null)      = true
	 * StringUtil.isBlank(&quot;&quot;)        = true
	 * StringUtil.isBlank(&quot; &quot;)       = true
	 * StringUtil.isBlank(&quot;bob&quot;)     = false
	 * StringUtil.isBlank(&quot;  bob  &quot;) = false
	 * </pre>
	 *
	 * @param str
	 *            目标字符串,或者Null
	 * @return <code>true</code> 如果目标字符串,是null,("")或空格
	 */
	public static boolean isBlank(String str) {
		return org.apache.commons.lang3.StringUtils.isBlank(str);
	}

	/**
	 * <p>
	 * 检查字符串,字符串不为空格,不为("") 或不为null
	 * </p>
	 *
	 * <pre>
	 * StringUtil.isNotBlank(null)      = false
	 * StringUtil.isNotBlank(&quot;&quot;)        = false
	 * StringUtil.isNotBlank(&quot; &quot;)       = false
	 * StringUtil.isNotBlank(&quot;bob&quot;)     = true
	 * StringUtil.isNotBlank(&quot;  bob  &quot;) = true
	 * </pre>
	 *
	 * @param str
	 *            目标字符串,或Null
	 * @return <code>true</code> 如果目标字符串不为("")和不为null和不为空格
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 替换一个字符串中第一次出现的keywords
	 *
	 * @param source
	 *            需要修改的字符串
	 * @param keyWords
	 *            需要替换的关键字
	 * @param targetWords
	 *            替换后的关键字
	 * @return
	 */
	public static String replaceIndexKeyWords(String source, String keyWords,
											  String targetWords) {
		if (!isBlank(keyWords)) {
			if (keyWords.startsWith("?")) {
				return source;
			}
		}
		// if(isBlank(source)){
		// return "";
		// }else{
		// if(source.indexOf(keyWords)!=-1){
		// String front= source.substring(0,
		// source.indexOf(keyWords)+keyWords.length());
		// String back =
		// source.substring(source.indexOf(keyWords)+keyWords.length());
		// return front.replaceAll(keyWords, targetWords)+back;
		// }else{
		// return source;
		// }
		// }
		return (source == null ? "" : source).replaceAll((keyWords == null ? ""
				: keyWords), (targetWords == null ? "" : targetWords));
	}

	/**
	 * 把文本编码为Html代码
	 *
	 * @param target
	 * @return 编码后的字符串
	 */
	public static String htmEncode(String target) {
		if (org.apache.commons.lang3.StringUtils.isBlank(target)) {
			return "";
		}
		StringBuffer stringbuffer = new StringBuffer();
		int j = target.length();
		for (int i = 0; i < j; i++) {
			char c = target.charAt(i);
			switch (c) {
				case 39:
					stringbuffer.append("&#39;");
					break;
				case 60:
					stringbuffer.append("&lt;");
					break;
				case 62:
					stringbuffer.append("&gt;");
					break;
				case 38:
					stringbuffer.append("&amp;");
					break;
				case 34:
					stringbuffer.append("&quot;");
					break;
				case 169:
					stringbuffer.append("&copy;");
					break;
				case 174:
					stringbuffer.append("&reg;");
					break;
				case 165:
					stringbuffer.append("&yen;");
					break;
				case 8364:
					stringbuffer.append("&euro;");
					break;
				case 8482:
					stringbuffer.append("&#153;");
					break;
				case 13:
					if (i < j - 1 && target.charAt(i + 1) == 10) {
						stringbuffer.append("<br>");
						i++;
					}
					break;
				case 32:
					if (i < j - 1 && target.charAt(i + 1) == ' ') {
						stringbuffer.append(" &nbsp;");
						i++;
						break;
					}
				default:
					stringbuffer.append(c);
					break;
			}
		}
		return new String(stringbuffer.toString());
	}

	/**
	 * 将数组转换为字符串
	 *
	 * @param array
	 * @return
	 */
	public static String arrayToString(String[] array) {
		StringBuffer str = new StringBuffer();
		if (array != null) {
			for (String string : array) {
				str.append(string);
			}
		}
		return str.toString();
	}

	/**
	 * 替换单引号(')，分号(;) 和 注释符号(--) 用于防止sql注入
	 * @param str
	 * @return
	 */
	public static Boolean transactSQLInjection(String str) {
		//return str.replaceAll(".*([';]+|(--)+).*", " ");
		String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
		return Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(str).find();
	}

	/**
	 * <p>
	 * 截取字符串从指定的位置开始到指定的结束位置
	 * </p>
	 * <p>
	 * 如果开始位置大于结束位置那么则返回<code> "" </code>
	 * </p>
	 * <p>
	 * 如果目标字符串为<code>null</code> 则返回<code>null</code>
	 * </p>
	 *
	 * <pre>
	 * StringUtil.substring(null, *, *)    = null
	 * StringUtil.substring(&quot;&quot;, * ,  *)    = &quot;&quot;;
	 * StringUtil.substring(&quot;abc&quot;, 0, 2)   = &quot;ab&quot;
	 * StringUtil.substring(&quot;abc&quot;, 2, 0)   = &quot;&quot;
	 * StringUtil.substring(&quot;abc&quot;, 2, 4)   = &quot;c&quot;
	 * StringUtil.substring(&quot;abc&quot;, 4, 6)   = &quot;&quot;
	 * StringUtil.substring(&quot;abc&quot;, 2, 2)   = &quot;&quot;
	 * StringUtil.substring(&quot;abc&quot;, -2, -1) = &quot;b&quot;
	 * StringUtil.substring(&quot;abc&quot;, -4, 2)  = &quot;ab&quot;
	 * </pre>
	 *
	 * @param str
	 *            目标字符串,或者Null
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @return 如果开始位置大于结束位置那么返回<code>""</code>,如果目标字符串为<code>null</code>那么则返回<code>null</code>
	 */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}
		if (end < 0) {
			end = str.length() + end;
		}
		if (start < 0) {
			start = str.length() + start;
		}
		if (end > str.length()) {
			end = str.length();
		}
		if (start > end) {
			return "";
		}
		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}
		if (end < str.length()) {
			return str.substring(start, end);
		}
		return str.substring(start, end);
	}

	/**
	 * access_token=F553148995D97D39357A841DAF25D398&expires_in=7776000
	 * 根据标识（sign）拆分字符串（str）为数组(String[])
	 * 将access_token=F553148995D97D39357A841DAF25D398和expires_in=7776000放入map中
	 * 再将map存入List中
	 * @param str
	 * @param sign
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<HashMap<String,Object>> splitBySign(String str , String sign ,String regex){
		String[] strs = str.split(sign);
		List<HashMap<String,Object>>  params =  new ArrayList<HashMap<String,Object>>();
		for(String temp:strs){
			String[] para = temp.split(regex);
			HashMap hm = new HashMap();
			hm.put(para[0], para[1]);
			params.add(hm);
		}
		return params;
	}

	/**
	 * 参数编码
	 *
	 * @param s
	 * @return
	 */
	public static String encode(String s) {
		if (s == null) {
			return "";
		}
		try {
			return URLEncoder.encode(s, "UTF-8").replace("+", "%20").replace(
					"*", "%2A").replace("%7E", "~").replace("#", "%23");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 参数反编码
	 *
	 * @param s
	 * @return
	 */
	public static String decode(String s) {
		if (s == null) {
			return "";
		}
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	/**
	 * 字符串编码转换
	 * @param str
	 * @return
	 */
	public static String encodeStr(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断字符串是否存在于这个字符数组中
	 * @param1 string array
	 * @param2 string
	 * @return boolean
	 */
	public static boolean contains(String[] array,String str){

		if(array==null||array.length==0){
			//数组里没东西则返回false
			return false;
		}else if(isBlank(str)){
			//数组有东西，而字符串空白，则返回true。 同样，如果字符串为null，返回true。
			return true;
		}

		for(int i=0,j=array.length;i<j;i++){
			if(array[i].equals(str))
				return true;
		}
		return false;
	}

	/**
	 * 返回一个不为 null 的字符串  如果字符串为空
	 * @param
	 * @return
	 */
	public static String avoidNull(String str){
		return str==null?"":str;
	}

	/**
	 * 去掉换行回车符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str){
		Pattern p = Pattern.compile("\\t|\r|\n");
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}

	/**
	 * <b>getNumbers</b><br/>
	 * <p>提取字符串中数字</p>
	 * <b>date：</b>2014-5-27 下午5:20:02<br/>
	 *
	 * @param content
	 * @return
	 *
	 * @exception
	 * @since  1.0
	 */
	public static String getNumbers(String content) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	/**
	 * 转移字符串，在双引号加上转移字符
	 * @param str
	 * @return
	 */
	public static String escapeString(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}else {
			return StringUtils.replaceAll(str, "\"", "\\\"");
		}
	}

	/**
	 * 反转字符串
	 *
	 * @param str
	 * @return
	 */
	public static String revertEscapeString(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}else {
			return StringUtils.replaceAll(str, "\\\"", "\"");
		}
	}
}
