package com.xiaoshanghai.nancang.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 根据出生日期计算年龄的工具类BirthdayToAgeUtil
 */
public class BirthdayToAgeUtil {

    private static String birthday;
    private static String ageStr;
    private static int age;
    //出生年、月、日
    private static int year;
    private static int month;
    private static int day;

    public static String BirthdayToAge(String birthday1) {
        birthday = birthday1;
        stringToInt(birthday, "yyyy-MM-dd");
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);
        // 用当前年月日减去出生年月日
        int yearMinus = yearNow - year;
        int monthMinus = monthNow - month;
        int dayMinus = dayNow - day;
        age = yearMinus;// 先大致赋值
        if (yearMinus <= 0) {
            age = 0;
            ageStr = String.valueOf(age);
            return ageStr;
        }
        if (monthMinus < 0) {
            age = age - 1;
        } else if (monthMinus == 0) {
            if (dayMinus < 0) {
                age = age - 1;
            }
        }
        ageStr = String.valueOf(age);
        return ageStr;
    }

    /**
     * String类型转换成date类型
     * strTime: 要转换的string类型的时间，
     * formatType: 要转换的格式yyyy-MM-dd HH:mm:ss
     * //yyyy年MM月dd日 HH时mm分ss秒，
     * strTime的时间格式必须要与formatType的时间格式相同
     */
    private static Date stringToDate(String strTime, String formatType) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatType);
            Date date;
            date = formatter.parse(strTime);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * String类型转换为long类型
     * .............................
     * strTime为要转换的String类型时间
     * formatType时间格式
     * formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     * strTime的时间格式和formatType的时间格式必须相同
     */
    private static void stringToInt(String strTime, String formatType) {
        try {
            //String类型转换为date类型
            Calendar calendar = Calendar.getInstance();
            Date date = stringToDate(strTime, formatType);
            calendar.setTime(date);
            if (date == null) {
            } else {
                //date类型转成long类型
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH) + 1;
                day = calendar.get(Calendar.DAY_OF_MONTH);
            }
        } catch (Exception e) {
        }
    }


    /**
     * 通过出生 月日获取星座
     * @param month
     * @param day
     * @return
     */
    public static String star(int month, int day) {
        String star = "";
        if (month == 1 && day >= 20 || month == 2 && day <= 18) {
            star = "水瓶座";
        }
        if (month == 2 && day >= 19 || month == 3 && day <= 20) {
            star = "双鱼座";
        }
        if (month == 3 && day >= 21 || month == 4 && day <= 19) {
            star = "白羊座";
        }
        if (month == 4 && day >= 20 || month == 5 && day <= 20) {
            star = "金牛座";
        }
        if (month == 5 && day >= 21 || month == 6 && day <= 21) {
            star = "双子座";
        }
        if (month == 6 && day >= 22 || month == 7 && day <= 22) {
            star = "巨蟹座";
        }
        if (month == 7 && day >= 23 || month == 8 && day <= 22) {
            star = "狮子座";
        }
        if (month == 8 && day >= 23 || month == 9 && day <= 22) {
            star = "处女座";
        }
        if (month == 9 && day >= 23 || month == 10 && day <= 23) {
            star = "天秤座";
        }
        if (month == 10 && day >= 24 || month == 11 && day <= 22) {
            star = "天蝎座";
        }
        if (month == 11 && day >= 23 || month == 12 && day <= 21) {
            star = "射手座";
        }
        if (month == 12 && day >= 22 || month == 1 && day <= 19) {
            star = "摩羯座";
        }
        return star;
    }
}