package com.xiaoshanghai.nancang.utils;

public class NumberUtils {

    public static String NumberToStr(String num) {

        Double aDouble = Double.valueOf(num + "");
        if (aDouble >= 10000) {
            double d = aDouble / 10000;
            String format = String.format("%.2f", d);
            return format + "万";
        } else {
            return String.format("%.2f", aDouble);
        }
    }

    public static String strToNumber(String num) {
        Double aDouble = Double.valueOf(num + "");
        if (aDouble >= 10000) {
            double d = aDouble / 10000;
            String format = String.format("%.2f", d);
            return format + "万";
        } else {
            return String.format("%d",((int)(aDouble.doubleValue())));
        }
    }
}
