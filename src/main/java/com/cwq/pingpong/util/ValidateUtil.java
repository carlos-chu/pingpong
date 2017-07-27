package com.cwq.pingpong.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate Utilities.
 *
cwq
 * @version $ v1.0 Sep 17, 2014 $
 */
public class ValidateUtil {

    public static final String AMT_CONSTRUCTURE = "^0|(([1-9][0-9]{0,9}|0)(\\.[0-9]{2}))$";
    private static Pattern AMT_PATTERN = Pattern.compile(AMT_CONSTRUCTURE);

    /**
     * 判断字符串是否为NULL或空字符串
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return value == null || "".equals(value.trim());
    }

    public static boolean isNumeric(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是数字字符串
     *
     * @param value
     * @return
     */
    public static boolean isDigit(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断长度是否超限和是否是数字字符串，但不能作为是否在整型范围 内的判断
     *
     * @param value
     * @param nLen
     * @return
     */
    public static boolean isDigit(String value, int nLen) {
        if (value.length() > nLen)
            return false;
        return isDigit(value);
    }

    /**
     * 判断是否是数字或字母或数字字母组合字符串,并且在指定长度内
     *
     * @param value
     * @param nLen
     * @return
     */
    public static boolean isLetterOrDigit(String value, int nLen) {
        if (value.length() > nLen)
            return false;
        return isLetterOrDigit(value);
    }

    /**
     * 判断是否是数字或字母或数字字母组合字符串
     *
     * @param value
     * @return
     */
    public static boolean isLetterOrDigit(String value) {
        String rule = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(rule);
        return pattern.matcher(value).matches();
    }

    public static boolean isOkPmCodeTppCodeBankCode(String value, int nLen) {
        if (value.length() > nLen)
            return false;
        String rule = "^[A-Za-z0-9\\_]+$";
        Pattern pattern = Pattern.compile(rule);
        return pattern.matcher(value).matches();
    }

    /**
     * 判断是否是纯字母字符串,并在指定长度内
     *
     * @param value
     * @param nLen
     * @return
     */
    public static boolean isLetter(String value, int nLen) {
        if (value.length() > nLen)
            return false;
        return isLetter(value);
    }

    public static boolean isLetter(String value) {
        String rule = "^[A-Za-z]+$";
        Pattern pattern = Pattern.compile(rule);
        return pattern.matcher(value).matches();
    }

    /**
     * 手机验证
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断字符串里是否有非法字符
     *
     * @param value
     * @return
     */
    public static boolean isRightString(String value) {
        if (isEmpty(value)) {
            return false;
        }
        try {
            Matcher charMatcher = Pattern.compile(".*[<>\\?\\*\'\"].*", Pattern.CASE_INSENSITIVE).matcher(value);
            if (charMatcher.matches()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 流水号规则字母|数字|点|中横线|下划线,组合或非组合都为合法
     *
     * @param value
     * @return
     */
    public static boolean isSerialNumber(String value) {
        String rule = "^[A-Za-z0-9\\.\\-\\_]+$";
        Pattern p = Pattern.compile(rule);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * 判断是否是合法金额
     *
     * @param value
     * @return
     */
    public static boolean isAmount(String value) {
        if (value == null || value.length() > 12)
            return false;
        return AMT_PATTERN.matcher(value).matches();
    }

    /**
     * 判断分账公式是否合法
     *
     * @param value
     *            字符串
     * @return boolean
     */
    public static boolean isFzData(String value) {
        String rule = "[\\d,.;A-Za-z]+";
        Pattern pattern = Pattern.compile(rule);
        return pattern.matcher(value).matches();
    }

    public static boolean isUrl(String value) {
        String regex = "(http|ftp|https)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        Pattern patternOne = Pattern.compile(regex);
        if (patternOne.matcher(value).matches()) {
            return true;
        }
        String regExThree = "(http|ftp|https)?://(\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b)+(/[\\w- ./?%&=]*)?";
        Pattern patternThree = Pattern.compile(regExThree);
        if (patternThree.matcher(value).matches()) {
            return true;
        }
        String regExFour = "(http|ftp|https)?://[A-Za-z]+(/[\\w- ./?%&=]*)?";
        Pattern patternFour = Pattern.compile(regExFour);
        if (patternFour.matcher(value).matches()) {
            return true;
        }
        String regExTwo = "(http|ftp|https)?://(\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b)+[:\\d]+(/[\\w- ./?%&=]*)?";
        Pattern patternTwo = Pattern.compile(regExTwo);
        if (patternTwo.matcher(value).matches()) {
            return true;
        }
        String regExFive = "(http|ftp|https)?://([\\w-]+\\.)+[\\w-]+[:\\d]+(/[\\w- ./?%&=]*)?";
        Pattern patternFive = Pattern.compile(regExFive);
        if (patternFive.matcher(value).matches()) {
            return true;
        }
        return false;
    }

    public static boolean isCardNo(String value) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        return pattern.matcher(value).matches();
    }

    public static boolean isIp(String value) {
        String regex = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }

    /**
     * 是否是指定格式的日期字符串
     *
     * @param value
     * @param pattern
     * @return
     */
    public static boolean isDateString(String value, String pattern) {
        if (isEmpty(value) || isEmpty(pattern))
            return false;
        if (value.length() != pattern.length())
            return false;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            sdf.setLenient(false);
            sdf.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isDate(Object source) {
        if (null == source)
            return false;
        if (source instanceof Date)
            return true;
        if (source instanceof String) {
            String val = source.toString();
            if (isDateString(val, "yyyyMMddHHmmss")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定的字符串必须在指定的字符串数组中才返回TRUE,否则FALSE 注意,调用该方法至少传两个参数,否则永远是FALSE
     *
     * @param value
     * @param strs
     * @return
     */
    public static boolean isRuleStr(String value, String... strs) {
        if (value == null || "".equals(value))
            return false;
        for (String str : strs) {
            if (value.equals(str))
                return true;
        }
        return false;
    }

    /**
     * @param value
     * @return
     */
    public static boolean isValidate(String value) {
        if (!isDigit(value, 4)) {
            return false;
        } else {
            String monthString = value.substring(2);
            if (Integer.parseInt(monthString) > 12 || Integer.parseInt(monthString) <= 0) {
                return false;
            }
            String yearString = value.substring(0, 2);
            String nowyearString = getYear();
            if (Integer.parseInt(yearString) < Integer.parseInt(nowyearString)) {
                return false;
            }
            if ((nowyearString.equals(yearString) && Integer.parseInt(monthString) < getMonth())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 返回当前年份后两位
     */
    public static String getYear() {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new java.util.Date());
        String year = "" + ca.get(Calendar.YEAR);
        return year.substring(2);
    }

    /**
     * @return
     */
    public static int getMonth() {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new java.util.Date());
        return ca.get(Calendar.MONTH) + 1;
    }

    /**
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
