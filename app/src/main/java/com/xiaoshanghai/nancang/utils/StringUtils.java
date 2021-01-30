package com.xiaoshanghai.nancang.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isTrimEmpty(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断字符串是否为null或全为空白字符
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空白字符<br> {@code false}: 不为null且不全空白字符
     */
    public static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 去除特殊字符
     *
     * @param world  字符串
     * @param perfix 字符
     * @return
     */
    public static String trimSuffixWorld(String world, String perfix) {
        return trimSuffixWorld(world, perfix, "");
    }

    /**
     * 去除特殊字符
     *
     * @param world        字符串
     * @param perfix       字符
     * @param replaceWorld 替换的字符
     * @return
     */
    public static String trimSuffixWorld(String world, String perfix, String replaceWorld) {
        if (isEmpty(world)) {
            return "";
        }
        return world.replaceAll(perfix, replaceWorld);
    }

    /**
     * 转换HTML标签
     *
     * @param html
     * @return
     */
    public static Spanned formatHtml(String html) {
        return Html.fromHtml(html);
    }

    /**
     * 转换HTML标签
     *
     * @param html
     * @return
     */
    public static Spanned formatHtml(String html, Object... param) {
        return Html.fromHtml(
                String.format(Locale.getDefault(), html,
                        param));
    }

    /**
     * 复制到剪切板
     *
     * @param mContext 上下文
     * @param word     文本
     */
    public static void copyToClipboard(Context mContext, String word) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", word);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    public static String replaceEvenWord(String word) {
        if (isEmpty(word)) {
            return word;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (i % 2 != 0) {
                sb.append("*");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String replaceX(String word, int count) {
        if (isEmpty(word)) {
            return word;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < word.length(); i++) {
            if (i > word.length() - (count + 1)) {
                sb.append("*");
            } else {
                sb.append(word.charAt(i));
            }
        }
        return sb.toString();
    }

    //根据身份证号输出年龄
    public static String IdNOToAge(String IdNO) {
        if (TextUtils.isEmpty(IdNO)) {
            return "";
        }
        if (IdNO.length() == 18) {
            String year = IdNO.substring(6, 10);// 得到年份
            String yue = IdNO.substring(10, 12);// 得到月份
            Date date = new Date();// 得到当前的系统时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String fyear = format.format(date).substring(0, 4);// 当前年份
            String fyue = format.format(date).substring(5, 7);// 月份
            // String fday=format.format(date).substring(8,10);
            int age = 0;
            if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
                age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
            } else {// 当前用户还没过生
                age = Integer.parseInt(fyear) - Integer.parseInt(year);
            }
            return String.valueOf(age);
        } else {

            String uyear = "19" + IdNO.substring(6, 8);// 年份
            String uyue = IdNO.substring(8, 10);// 月份
            // String uday=card.substring(10, 12);//日
            String usex = IdNO.substring(14, 15);// 用户的性别
            Date date = new Date();// 得到当前的系统时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String fyear = format.format(date).substring(0, 4);// 当前年份
            String fyue = format.format(date).substring(5, 7);// 月份
            // String fday=format.format(date).substring(8,10);
            int age = 0;
            if (Integer.parseInt(uyue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
                age = Integer.parseInt(fyear) - Integer.parseInt(uyear) + 1;
            } else {// 当前用户还没过生
                age = Integer.parseInt(fyear) - Integer.parseInt(uyear);
            }
            return String.valueOf(age);
        }
    }


    /**
     * 去除空
     *
     * @param str
     * @return
     */
    public static String strNoNull(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        } else {
            return str;
        }
    }

    public static boolean isEmptyAndSpace(String str) {
        if (TextUtils.isEmpty(str)) return true;
        if (str.trim().isEmpty()) return true;
        return false;
    }

    /**
     * 获取json文本
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    // 根据Unicode编码判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }


    public static String getSha1(String str) {
        if (null == str || 0 == str.length()) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * @param string
     * @return
     */
    public static String md5(String string) {
        if (StringUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String map2HttpParam(Map<String, String> map) {
        if (map == null) return "";
        StringBuilder sb = new StringBuilder();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            String value = map.get(key);
            sb.append(key);
            sb.append("=");
            sb.append(value);
            sb.append("&");
        }
        if (sb.length() > 0)
            return sb.delete(sb.length() - 1, sb.length()).toString();
        return "";
    }

    /**
     * 判断是否为全数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static void main(String args[]) {
        System.out.println(getSha1(md5("app_name=萝卜助贷&app_version=1.1&channel=luobozhudai&platform=iOS&system=iPhoneOS&system_version=12.3.2&timestamp=1563778450&key=e1Y9A$hWyOR#pfi5wZOq#H6Hz@v00bbA")));
    }

    /**
     * 正则判断emoji表情
     *
     * @param input
     * @return
     */
    public static boolean isEmoji(String input) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\ud83e\udc00-\ud83e\udfff]|[\u2100-\u32ff]|[\u0030-\u007f][\u20d0-\u20ff]|[\u0080-\u00ff]|[`!@#$%^&*()+=|{}':;'\\[\\]<>/?~！#￥%……&*（）——+|{}【】‘；：”“’、？]");
        Matcher m = p.matcher(input);
        return m.find();
    }


    /**
     * 简单校验印尼名字 大于2位 只能为字母
     */
    public static boolean isName(String name) {
        if (TextUtils.isEmpty(name)) return false;
        String regix = "^[ a-zA-Z]{2,}$";
        return name.trim().matches(regix);
    }

    /**
     * 简单校验印尼身份证
     * 1. 规则：位数必须为16位，纯数字
     * 1-6为行政区号，暂无校验表；
     * 7-12位为出生日期
     * 7-8：日，范围为01-31、41-71
     * 9-10：月，范围为01-12
     * 11-12：年，范围为00-99
     * 13-16为编号码，无需校验
     *
     * @param cardNo
     * @return
     */
    public static boolean isIdCard(String cardNo) {
        if (TextUtils.isEmpty(cardNo)) return false;
        String regix = "^[1-9]([0-9]{5})([0-2]\\d|30|31|4[1-9]|[5-6][0-9]|70|71)(0[1-9]|1[0-2])([0-9]{2})([0-9]{4})$";
        return cardNo.trim().matches(regix);
    }

    /**
     * 简单校验月收入
     * 填写项校验：纯数字 正整数 非0，5≤位数≤9
     *
     * @param salary
     * @return
     */
    public static boolean isMonthlySalary(String salary) {
        if (TextUtils.isEmpty(salary)) return false;
        String regix = "^[1-9]([0-9]{4,8})$";
        return salary.trim().matches(regix);
    }

    /**
     * 简单校验地址
     * 规则
     * 2-100个字符
     * 只能有英文字母、数字、空格、符号. ，& /
     * （）
     * 符号必须为英文格式
     *
     * @param address
     * @return
     */
    public static boolean isAddress(String address) {
        if (TextUtils.isEmpty(address)) return false;
        String regix = "^[ A-Za-z0-9.,/;&()]{2,100}$";
        return address.trim().matches(regix);
    }

    /**
     * 简单验证银行卡格式
     * 规则：
     * 无规则 10<= 位数 <=16
     *
     * @param cardNo
     * @return
     */
    public static boolean isBankCard(String cardNo) {
        if (TextUtils.isEmpty(cardNo)) return false;
        String regix = "^[0-9]{10,16}$";
        return cardNo.trim().matches(regix);
    }

    public static boolean isPhoneNum(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) return false;
        String regix = "^1[0-9]{10}$";
        return phoneNum.trim().matches(regix);
    }
}
