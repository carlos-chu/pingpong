package com.cwq.pingpong.util;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class StringUtil {

	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	public static String getNullStringVal(String val) {
		if (isEmpty(val))
			return "";
		return val;
	}

	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否为NULL和空串和"null"
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		return !notEmpty(str);
	}

	public static String retrievePayIdFromSharaData(String payId) {
		if (isEmpty(payId))
			return payId;
		String[] colons = payId.split(";");
		for (int i = 0; i < colons.length; i++) {
			String s = colons[i];
			if (!StringUtil.isEmpty(s)) {
				int commapos = payId.indexOf(",");
				if (-1 != commapos) {
					String[] splitedPayId = s.split(",");
					return splitedPayId[1];
				}
			}
		}
		return payId;
	}

	/**
	 * 判断字符串是否不为NULL和空串和"null"。
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean notEmpty(String str) {
		return str != null && str.trim().length() > 0 && !str.toLowerCase().equals("null");

	}

	/**
	 * 判断字符串是否不为NULL和空串。 字符串可以为“null”、“NULL”
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean notEmptyStrnull(String str) {
		return str != null && str.trim().length() > 0;
	}

	/**
	 * 判断字符串是否为NULL和空串。 字符串可以为“null”、“NULL”
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isEmptyStrnull(String str) {
		return !notEmptyStrnull(str);
	}

	/**
	 * 判断输入的object为空
	 * 
	 * @param Object
	 *            输入字符串
	 * @return boolean
	 */
	public static boolean isEmpty(Object object) {
		return (object == null) || object.toString().trim().length() == 0;
	}

	/**
	 * 判断输入的object不为空
	 * 
	 * @param object
	 * @return boolean
	 */
	public static boolean notEmpty(Object object) {
		return !isEmpty(object);
	}

	/**
	 * 若String是一个空对象，则返回“”
	 * 
	 * @param String
	 * @return String
	 */
	public static String trimString(String str) {
		if (str == null) {
			str = "";
		}
		return str.trim();
	}

	/**
	 * 将对象转成字符串并去除空格
	 * 
	 * @param object
	 * @return
	 */
	public static String toStringAndTrim(Object object) {
		if (object == null) {
			return "";
		} else {
			return object.toString().trim();
		}
	}

	/**
	 * 格式化手机号,隐藏中间四位
	 * 
	 * @param String
	 * @return String
	 */
	public static String formatMobile(String mobile) {
		if (mobile == null || "".equals(mobile.trim())) {
			return null;
		}
		String result = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
		return result;
	}

	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param String
	 *            待转字符串
	 * @param String
	 *            分隔符
	 * @return String[]
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param String
	 *            待转字符串
	 * @return String[]
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	/**
	 * 将字符串值转化为map
	 * 
	 * @param text字符串
	 * @param splitChar按照末个字符
	 * @return map
	 */
	public static Map<String, String> string2Map(String text, String splitChar) {
		Map<String, String> param = new HashMap<String, String>();
		if (isEmpty(text) || isEmpty(splitChar))
			return param;
		String[] stemps = text.split(splitChar);
		String[] temp = null;
		for (int i = 0; i < stemps.length; i++) {
			temp = stemps[i].split("=");
			if (temp.length >= 2) {
				param.put(temp[0], temp[1]);
				temp = null;
			} else {
				param.put(temp[0], "");
			}
		}
		return param;
	}

	/**
	 * 方法说明:将Map<String, String>转为String，用“=”连接K-V值，“&”间隔，长度最长为512
	 * 
	 * @param paramMap
	 * @return String
	 */
	public static String concatMap(Map<String, String> paramMap) {
		if (paramMap == null)
			return "";
		StringBuilder sbuf = new StringBuilder(512);
		Set<Entry<String, String>> set = paramMap.entrySet();
		Iterator<Entry<String, String>> it = set.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			sbuf.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		if (sbuf.length() > 0)
			sbuf.deleteCharAt(sbuf.length() - 1);
		return sbuf.toString();
	}

	/**
	 * 判断字符串是否为数字组成
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[0-9]+$");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断长度是否超限和是否是数字字符串，但不能作为是否在整型范围 内的判断
	 * 
	 * @param string
	 * @return boolean
	 */
	public static boolean isNumeric(String string, int len) {
		if (string.length() > len)
			return false;
		return isNumeric(string);
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 * 
	 * @param String
	 * @return boolean
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile(
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 邮箱判断
	 * 
	 * @param String
	 *            email
	 * @return boolean
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		logger.info(m.matches() + "---");
		return m.matches();
	}

	/**
	 * 手机号码验证
	 * 
	 * @param String
	 *            mobiles
	 * @return boolean
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");
		Matcher m = p.matcher(mobiles);
		logger.info(m.matches() + "---");
		return m.matches();
	}

	/**
	 * URL验证
	 * 
	 * @param String
	 *            url
	 * @return boolean
	 */
	public static boolean isUrl(String url) {
		if (isEmpty(url))
			return true;
		if (url.length() < 4 || !url.trim().substring(0, 4).toLowerCase().equals("http"))
			return true;
		return false;
	}

	/**
	 * 计算字符串的长度，包括汉字在内
	 * 
	 * @param String
	 *            value
	 * @return int
	 */
	public static int String_length(String value) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/***
	 * 在字符串中分别以指定的字符串为首尾截取字符串
	 * 
	 * @param temp
	 * @param beginStr
	 * @param endStr
	 * @return String
	 */
	public static String subStr(String temp, String beginStr, String endStr) {
		if (isEmpty(temp) || isEmpty(beginStr) || isEmpty(endStr))
			return "";
		if (!temp.contains(beginStr) || !temp.contains(endStr))
			return "";

		return temp.substring(temp.indexOf(beginStr) + beginStr.length(), temp.indexOf(endStr));
	}

	/**
	 * 截取字符串从首部到指定长度的子字符串
	 * 
	 * @param str
	 * @param longth
	 * @return
	 */
	public static String getString(String str, int longth) {
		if (str == null || str.trim().length() <= 0) {
			return "";
		}
		if (str.length() > longth) {
			return str.substring(0, longth);
		}
		return str;
	}

	/***
	 * 根据指定小数点位数将double转为字符串
	 * 
	 * @param double
	 * @param fNumber
	 *            小数点的位数
	 * @return String
	 */
	public static String double2String(double d, int fNumber) {
		if (fNumber < 0)
			fNumber = 0;

		String pattern = null;
		switch (fNumber) {
		case 0:
			pattern = "#0"; //$NON-NLS-1$
			break;
		default:
			pattern = "#0."; //$NON-NLS-1$
			StringBuffer b = new StringBuffer(pattern);
			for (int i = 0; i < fNumber; i++) {
				b.append('0');
			}
			pattern = b.toString();
			break;

		}
		DecimalFormat formatter = new DecimalFormat();
		formatter.applyPattern(pattern);
		String value = formatter.format(d);
		return value;
	}

	/**
	 * 根据分隔符将字符串分隔并转化为map，对于没有值得K-V对，V默认为“”
	 * 
	 * @param String
	 *            responseContent字符串
	 * @param String
	 *            splitChar按照末个字符
	 * @return Map<String, String>
	 */
	public static Map<String, String> convertMap(String responseContent, String splitChar) throws Exception {
		Map<String, String> retMap = new HashMap<String, String>();
		if (responseContent == null || "".equals(responseContent)) {
			return null;
		}
		String[] results = responseContent.split(splitChar);
		for (String stemp : results) {
			String[] maps = stemp.split("=");
			if (maps != null && maps.length >= 1) {
				if (maps.length >= 2) {
					retMap.put(maps[0], maps[1]);
				} else {
					retMap.put(maps[0], "");
				}
			}
		}
		return retMap;
	}

	/**
	 * 返回给商户的结果串
	 * 
	 * @param map
	 * @param encodeFlag
	 * @return
	 */
	public static String retMerchantString(Map map) {
		String ret = "";
		Iterator entries = map.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else {
				value = valueObj.toString();
				try {
					value = URLEncoder.encode(value, "UTF-8");
				} catch (Exception e) {

				}
			}
			if (ret.length() <= 0)
				ret = name + "=" + value;
			else
				ret = ret + "&" + name + "=" + value;
		}

		return ret;
	}

	/**
	 * 将map的值转换成字符串
	 * 
	 * @param map
	 * @param encodeFlag
	 *            是否编码
	 * @return
	 */
	public static String map2String(Map map) {
		String ret = "";
		if (map == null || map.isEmpty()) {
			return ret;
		}
		Iterator entries = map.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			ret = ret + name + "=" + value + ";";
		}

		return ret;
	}

	/**
	 * 默认以“;”分隔每个K-V
	 * 
	 * @param Map
	 *            <String, String>
	 * @return String
	 */
	public static String printMap(Map<String, String> map) {
		String ret = "";
		if (map == null)
			return ret;

		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = map.get(key);
			if (ret.length() > 0)
				ret = ret + ";";
			ret = ret + key + "=" + value;
		}

		return ret;
	}

	/**
	 * 以splitString为分隔，返回K-V串
	 * 
	 * @param Map
	 *            <String, String>
	 * @param String
	 * @return String
	 */
	public static String printMap(Map<String, String> map, String splitString) {
		String ret = "";
		if (map == null)
			return ret;

		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = map.get(key);
			if (ret.length() > 0)
				ret = ret + splitString;
			ret = ret + key + "=" + value;
		}

		return ret;
	}

	/**
	 * 
	 * @param map
	 * @param safety
	 *            为TRUE，不打印敏感数据
	 * @return
	 */
	public static String showSensitiveParam(Map<String, String> map, boolean safety) {
		StringBuilder sbuf = new StringBuilder(128);
		Set<String> set = map.keySet();
		boolean flag = false;
		sbuf.append("[");
		for (Iterator<String> it = set.iterator(); it.hasNext(); flag = true) {
			String key = it.next();
			String value = map.get(key);
			if (safety) {
				if ("passwd".equals(key) || "pass_wd".equals(key) || "cvv2".equals(key) || "cvv".equals(key)) {
					if (value == null || "".equals(value)) {
						value = "";
					} else {
						value = "******";
					}
				}
				if (value == null)
					value = "";
				if (("card_id".equals(key) || "cardid".equals(key)) && value.length() > 6) {
					value = value.substring(0, 6) + "******";
				}
			}
			if (!flag) {
				sbuf.append(key).append("=").append(value);
			} else {
				sbuf.append(",").append(key).append("=").append(value);
			}
		}
		sbuf.append("]");
		return sbuf.toString();
	}

	/**
	 * 字符串数组转List
	 * 
	 * @param obj
	 * @return
	 */
	public static List<String> strToList(String[] obj) {
		if (obj != null && obj.length > 0) {
			List<String> objList = new ArrayList<String>(Arrays.asList(obj));
			return objList;
		}
		List<String> arrayList = new ArrayList<String>();
		return arrayList;
	}

	/***
	 * 替换字符串中指定字符為""
	 * 
	 * @param str
	 * @return
	 */
	public static String convertStr(String str, String oldStr) {
		return str.replace(oldStr, "");
	}

	public static String formatInputParam(String input) {
		if (input == null || "".equals(input.trim())) {
			return input;
		}

		String textStr = "";
		Matcher scriptMatcher;
		Matcher styleMatcher;
		Matcher htmlMatcher;
		try {
			// 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			String scriptReg = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

			// 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String styleReg = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

			// 定义HTML标签的正则表达式
			String htmlReg = "[<>].*";

			// 过滤script标签
			scriptMatcher = Pattern.compile(scriptReg, Pattern.CASE_INSENSITIVE).matcher(input);
			input = scriptMatcher.replaceAll("");

			// 过滤style标签
			styleMatcher = Pattern.compile(styleReg, Pattern.CASE_INSENSITIVE).matcher(input);
			input = styleMatcher.replaceAll("");

			// 过滤html标签
			htmlMatcher = Pattern.compile(htmlReg, Pattern.CASE_INSENSITIVE).matcher(input);
			input = htmlMatcher.replaceAll("");

			textStr = input;// replaceAll(" ", "");
			textStr = textStr.replaceAll("&hellip;&hellip;", "……");
			textStr = textStr.replaceAll("&nbsp;", "").trim();
			textStr = textStr.replaceAll("\"", "");
			textStr = textStr.replaceAll("\r", "");
			textStr.replaceAll("\n", "");
		} catch (Exception e) {
		}

		return textStr;
	}

	/**
	 * 过滤特殊字符
	 * 
	 * @param input
	 * @return
	 */
	public static boolean judgeInputParam(String input) {
		if (input == null || "".equals(input.trim())) {
			return true;
		}

		Matcher scriptMatcher;
		Matcher styleMatcher;
		Matcher htmlMatcher;
		Matcher charMatcher;
		try {
			// 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			String scriptReg = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

			// 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			String styleReg = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

			// 定义HTML标签的正则表达式
			String htmlReg = "[<>].*";

			charMatcher = Pattern.compile(".*[<>\\?\\*\'\"].*", Pattern.CASE_INSENSITIVE).matcher(input);
			if (charMatcher.matches()) {
				return false;
			}

			// 过滤script标签
			scriptMatcher = Pattern.compile(scriptReg, Pattern.CASE_INSENSITIVE).matcher(input);
			if (scriptMatcher.matches()) {
				return false;
			}

			// 过滤style标签
			styleMatcher = Pattern.compile(styleReg, Pattern.CASE_INSENSITIVE).matcher(input);
			if (styleMatcher.matches()) {
				return false;
			}

			// 过滤html标签
			htmlMatcher = Pattern.compile(htmlReg, Pattern.CASE_INSENSITIVE).matcher(input);
			if (htmlMatcher.matches()) {
				return false;
			}

			return true;
		} catch (Exception e) {
			logger.warn(" errMsg: " + e.getMessage());
			return false;
		}
	}

	// *****************************************************************************//
	// *****************************************************************************//

	private static final String EMAIL = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";

	private static final String DATE = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";

	private static final String NUMBER = "^[0-9]+$";

	private static final String MOBILE = "^(1[0-9])\\d{9}$";

	private static final String SCRIPTREG = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

	private static final String STYLEREG = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

	private static final String HTMLREG = "[<>].*";

	private static final String INTEGER = "^[-\\+]?[\\d]*$";

	private static final String DOUBLE = "^[-+]?(\\d+(\\.\\d*)?|(\\.\\d+))([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$";

	private static final String CHINESE = "[\u0391-\uFFE5]+$";

	private static final String CONTROL_CHARACTER = "\\s*|\t|\r|\n";

	public static boolean isEmpty1(String str) {
		return !notEmpty1(str);
	}

	public static boolean notEmpty1(String str) {
		return str != null && str.trim().length() > 0 && !str.toLowerCase().equals("null");
	}

	public static boolean isEmptyNull(String str) {
		return !notEmptyNull(str);
	}

	public static boolean notEmptyNull(String str) {
		return str != null && str.trim().length() > 0;
	}

	public static boolean isEmpyt1(Object obj) {
		return !notEmpty1(obj);
	}

	public static boolean notEmpty1(Object obj) {
		return obj != null && obj.toString().trim().length() > 0;
	}

	public static String trimString1(String str) {
		if (isEmptyNull(str)) {
			return "";
		} else {
			return str.trim();
		}
	}

	public static String trimObject(Object obj) {
		if (isEmpyt1(obj)) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}

	public static boolean isEmail1(String email) {
		if (isEmpty1(email)) {
			return false;
		} else {
			Pattern pattern = Pattern.compile(EMAIL);
			Matcher mathcer = pattern.matcher(email);
			return mathcer.matches();
		}
	}

	public static boolean isDate1(String date) {
		if (isEmpty1(date)) {
			return false;
		} else {
			Pattern pattern = Pattern.compile(DATE);
			Matcher mathcer = pattern.matcher(date);
			return mathcer.matches();
		}
	}

	public static boolean isNumeric1(String number) {
		if (isEmpty1(number)) {
			return false;
		} else {
			Pattern pattern = Pattern.compile(NUMBER);
			Matcher mathcer = pattern.matcher(number);
			return mathcer.matches();
		}
	}

	public static boolean isMobile1(String mobile) {
		if (isEmpty1(mobile)) {
			return false;
		} else {
			Pattern pattern = Pattern.compile(MOBILE);
			Matcher mathcer = pattern.matcher(mobile);
			return mathcer.matches();
		}
	}

	/**
	 * 判断是否为整数
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile(INTEGER);
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断是否为浮点数，包括double和float
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是浮点数返回true,否则返回false
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile(DOUBLE);
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断输入的字符串是否为纯汉字
	 * 
	 * @param str
	 *            传入的字符窜
	 * @return 如果是纯汉字返回true,否则返回false
	 */
	public static boolean isChinese(String str) {
		Pattern pattern = Pattern.compile(CHINESE);
		return pattern.matcher(str).matches();
	}

	public static String getHideMobileNum(String mobile) {
		if (isEmpty1(mobile) || !isMobile1(mobile)) {
			return "";
		} else {
			return mobile.substring(0, 3) + repeat("*", 4) + mobile.subSequence(7, 11);
		}
	}

	public static String getHideEmailPrefix(String email) {
		if (null != email) {
			int index = email.lastIndexOf('@');
			if (index > 0) {
				email = repeat("*", index).concat(email.substring(index));
			}
		}
		return email;
	}

	public static String repeat(String src, int num) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < num; i++) {
			s.append(src);
		}
		return s.toString();
	}

	/**
	 * 考虑包含汉字的情况
	 * 
	 * @param str
	 * @return
	 */
	public static int getStrLength(String str) {
		if (isEmptyNull(str)) {
			return 0;
		} else {
			int valueLength = 0;
			String chinese = "[\u4e00-\u9fa5]";
			for (int i = 0; i < str.length(); i++) {
				String temp = str.substring(i, i + 1);
				if (temp.matches(chinese)) {
					valueLength += 2;
				} else {
					valueLength += 1;
				}
			}
			return valueLength;
		}
	}

	public static String getSubString(String str, String bstr, String estr) {
		if (isEmptyNull(str) || isEmptyNull(bstr) || isEmptyNull(estr)) {
			return "";
		} else if (!str.contains(bstr) || !str.contains(estr)) {
			return "";
		} else {
			return str.substring(str.indexOf(bstr) + bstr.length(), str.indexOf(estr));
		}
	}

	public static String getSubString(String str, int begin, int end) {
		if (isEmptyNull(str) || str.length() < end || begin < 0 || end < 0) {
			return "";
		} else {
			return str.substring(begin, end);
		}
	}

	/**
	 * 
	 * @param str
	 * @param index
	 *            不考虑汉字的情况
	 * @return
	 */
	public static String getSubString(String str, int index) {
		if (isEmptyNull(str) || str.length() < index || index < 0) {
			return "";
		} else {
			return str.substring(0, index);
		}
	}

	public static List<String> stringToList(String str, String splitregex) {
		if (isEmptyNull(str) || isEmptyNull(splitregex)) {
			return null;
		}
		List<String> retlist = new ArrayList<String>();
		String[] strs = str.split(splitregex);
		if (notEmpty1(strs)) {
			for (int i = 0; i < strs.length; i++) {
				retlist.add(strs[i]);
			}
		}
		return retlist;
	}

	public static List<String> stringToList(String str) {
		if (isEmptyNull(str)) {
			return null;
		}
		return stringToList(str, "\\s*,\\s*");
	}

	public static List<String> stringToList(String[] obj) {
		if (isEmpyt1(obj)) {
			return null;
		}
		return new ArrayList<String>(Arrays.asList(obj));
	}

	public static String[] stringToArray(String str, String spiltregex) {
		if (isEmptyNull(str)) {
			return null;
		} else {
			return str.split(spiltregex);
		}
	}

	public static String[] stringToArray(String str) {
		if (isEmptyNull(str)) {
			return null;
		} else {
			return stringToArray(str, "\\s*,\\s*");
		}
	}

	public static Map<String, String> stringToMap(String str, String connectors, String splitregex,
			String defalutchar) {
		if (isEmptyNull(str) || isEmptyNull(connectors) || isEmptyNull(splitregex) || isEmptyNull(defalutchar)) {
			return null;
		}
		Map<String, String> returnMap = new HashMap<String, String>();
		String[] keyValue = str.split(splitregex);
		String[] temp = null;
		for (int index = 0; index < keyValue.length; index++) {
			temp = keyValue[index].split(connectors);
			if (temp.length >= 2) {
				returnMap.put(temp[0].trim(), temp[1].trim());
				temp = null;
			} else {
				returnMap.put(temp[0].trim(), defalutchar);
			}
		}
		return returnMap;
	}

	public static Map<String, String> stringToMap(String str, String connectors, String splitregex) {
		if (isEmptyNull(str) || isEmptyNull(splitregex) || isEmptyNull(connectors)) {
			return null;
		} else {
			return stringToMap(str, connectors, splitregex, "");
		}
	}

	public static Map<String, String> stringToMap(String str, String connectors) {
		if (isEmptyNull(str) || isEmptyNull(connectors)) {
			return null;
		} else {
			return stringToMap(str, connectors, "\\s*;\\s*", "");
		}
	}

	public static Map<String, String> stringToMap(String str) {
		if (isEmptyNull(str)) {
			return null;
		} else {
			return stringToMap(str, "\\s*=\\s*", "\\s*;\\s*", "");
		}
	}

	public static String mapToString(Map<String, String> map, String connectors, String splitregex) {
		if (isEmpyt1(map) || isEmpty1(connectors) || isEmpty1(splitregex)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Set<Entry<String, String>> set = map.entrySet();
		Iterator<Entry<String, String>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			sb.append(entry.getKey()).append(connectors).append(entry.getValue()).append(splitregex);
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String mapToString(Map<String, String> map, String connectors) {
		if (isEmpyt1(map) || isEmpty1(connectors)) {
			return "";
		} else {
			return mapToString(map, connectors, "&");
		}
	}

	public static String mapToString(Map<String, String> map) {
		if (isEmpyt1(map)) {
			return "";
		} else {
			return mapToString(map, "=", "&");
		}
	}

	public static String objectMapToString(Map<String, Object> map, String connectors, String splitregex) {
		if (isEmpyt1(map) || isEmpty1(connectors) || isEmpty1(splitregex)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Set<Entry<String, Object>> set = map.entrySet();
		Iterator<Entry<String, Object>> iterator = set.iterator();
		String key = "";
		String value = "";
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			key = entry.getKey();
			Object obj = entry.getValue();
			if (obj == null) {
				value = "";
			} else if (obj instanceof String[]) {
				String[] values = (String[]) obj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = obj.toString().trim();
			}
			sb.append(key).append(connectors).append(value).append(splitregex);
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String objectMapToString(Map<String, Object> map, String connectors) {
		if (isEmpyt1(map) || isEmpty1(connectors)) {
			return "";
		} else {
			return objectMapToString(map, connectors, "&");
		}
	}

	public static String objectMapToString(Map<String, Object> map) {
		if (isEmpyt1(map)) {
			return "";
		} else {
			return objectMapToString(map, "=", "&");
		}
	}

	public static String replaceString(String str, String replaced, String replace) {
		if (isEmpty1(str)) {
			return "";
		}
		return str.replaceAll(replaced, replace);
	}

	public static String replaceString(String str, String replaced) {
		if (isEmpty1(str)) {
			return "";
		}
		return replaceString(str, replaced, "");
	}

	public static String replaceControlCharacter(String str) {
		if (null != str) {
			Pattern p = Pattern.compile(CONTROL_CHARACTER);
			Matcher m = p.matcher(str);
			str = m.replaceAll("");
		}
		return str;
	}

	/**
	 * 去掉字符串中重复的子字符串
	 * 
	 * @param str
	 * @return String
	 */
	public static String removeSameString(String str) {
		Set<String> mLinkedSet = new LinkedHashSet<String>();
		String[] strArray = str.split(" ");
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < strArray.length; i++) {
			if (!mLinkedSet.contains(strArray[i])) {
				mLinkedSet.add(strArray[i]);
				sb.append(strArray[i] + " ");
			}
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	/**
	 * 全角字符转半角字符
	 * 
	 * @param QJStr
	 * @return String
	 */
	public static final String QJToBJChange(String QJStr) {
		char[] chr = QJStr.toCharArray();
		String str = "";
		for (int i = 0; i < chr.length; i++) {
			chr[i] = (char) ((int) chr[i] - 65248);
			str += chr[i];
		}
		return str;
	}

	public static int stringToInt(String s) {
		if (notEmptyNull(s)) {
			try {
				return Integer.parseInt(s);
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

	public static float stringToFloat(String s) {
		if (notEmptyNull(s)) {
			try {
				return Float.parseFloat(s);
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

	public static double stringToDouble(String s) {
		if (notEmptyNull(s)) {
			try {
				return Double.parseDouble(s);
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

	public static long stringToLong(String s) {
		if (notEmptyNull(s)) {
			try {
				return Long.parseLong(s);
			} catch (Exception e) {
				return 0L;
			}
		}
		return 0L;
	}

	public static String doubleToString(double d, int fNumber) {
		if (fNumber < 0)
			fNumber = 0;

		String pattern = null;
		switch (fNumber) {
		case 0:
			pattern = "#0"; //$NON-NLS-1$
			break;
		default:
			pattern = "#0."; //$NON-NLS-1$
			StringBuffer b = new StringBuffer(pattern);
			for (int i = 0; i < fNumber; i++) {
				b.append('0');
			}
			pattern = b.toString();
			break;

		}
		DecimalFormat formatter = new DecimalFormat();
		formatter.applyPattern(pattern);
		String value = formatter.format(d);
		return value;
	}

	public static String filterString(String source) {
		if (StringUtil.isEmpty(source)) {
			return "";
		}
		return source;
	}

	public static String filterInputString(String input) {
		if (isEmpty1(input)) {
			return input;
		}

		String textStr = "";
		Matcher scriptMatcher;
		Matcher styleMatcher;
		Matcher htmlMatcher;
		try {
			scriptMatcher = Pattern.compile(SCRIPTREG, Pattern.CASE_INSENSITIVE).matcher(input);
			input = scriptMatcher.replaceAll("");

			styleMatcher = Pattern.compile(STYLEREG, Pattern.CASE_INSENSITIVE).matcher(input);
			input = styleMatcher.replaceAll("");

			htmlMatcher = Pattern.compile(HTMLREG, Pattern.CASE_INSENSITIVE).matcher(input);
			input = htmlMatcher.replaceAll("");

			textStr = input;
			textStr = textStr.replaceAll("&hellip;&hellip;", "……");
			textStr = textStr.replaceAll("&nbsp;", "").trim();
			textStr = textStr.replaceAll("\"", "");
			textStr = textStr.replaceAll("\r", "");
			textStr.replaceAll("\n", "");
		} catch (Exception e) {

		}
		return textStr;
	}

	public static boolean verifyInputString(String input) {
		if (isEmpty1(input)) {
			return true;
		}

		Matcher scriptMatcher;
		Matcher styleMatcher;
		Matcher htmlMatcher;
		Matcher charMatcher;
		try {

			charMatcher = Pattern.compile(".*[<>\\?\\*\'\"].*", Pattern.CASE_INSENSITIVE).matcher(input);
			if (charMatcher.matches()) {
				return false;
			}

			scriptMatcher = Pattern.compile(SCRIPTREG, Pattern.CASE_INSENSITIVE).matcher(input);
			if (scriptMatcher.matches()) {
				return false;
			}

			styleMatcher = Pattern.compile(STYLEREG, Pattern.CASE_INSENSITIVE).matcher(input);
			if (styleMatcher.matches()) {
				return false;
			}

			htmlMatcher = Pattern.compile(HTMLREG, Pattern.CASE_INSENSITIVE).matcher(input);
			if (htmlMatcher.matches()) {
				return false;
			}

			return true;
		} catch (Exception e) {
			logger.warn(" errMsg: " + e.getMessage());
			return false;
		}
	}

	public static List<String> splitSimpleString(String source, char gap) {
		List<String> result = new LinkedList<String>();
		if (source == null)
			return result;
		char[] sourceChars = source.toCharArray();
		int startIndex = 0, index = -1;
		while (index++ != sourceChars.length) {
			if (index == sourceChars.length || sourceChars[index] == gap) {
				char[] section = new char[index - startIndex];
				System.arraycopy(sourceChars, startIndex, section, 0, index - startIndex);
				result.add(String.valueOf(section));
				startIndex = index + 1;
			}
		}
		return result;
	}

	public static String maskCardName(String cardName) {
		if (isEmpty(cardName)) {
			return "***";
		}
		if (cardName.length() > 1) {
			return cardName.substring(0, 1) + "**";
		} else {
			return "***";
		}
	}
}