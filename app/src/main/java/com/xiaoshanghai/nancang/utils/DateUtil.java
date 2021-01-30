package com.xiaoshanghai.nancang.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private DateUtil() {
    }

    /**
     * 枚举日期格式
     */
    public enum DatePattern {
        /**
         * 格式："yyyy-MM-dd HH:mm:ss"
         */
        ALL_TIME {
            public String getValue() {
                return "yyyy-MM-dd HH:mm:ss";
            }
        },
        /**
         * 格式："yyyy-MM"
         */
        ONLY_MONTH {
            public String getValue() {
                return "yyyy-MM";
            }
        },
        /**
         * 格式："yyyy-MM-dd"
         */
        ONLY_DAY {
            public String getValue() {
                return "yyyy-MM-dd";
            }
        },
        /**
         * 格式："yyyy-MM-dd HH"
         */
        ONLY_HOUR {
            public String getValue() {
                return "yyyy-MM-dd HH";
            }
        },
        /**
         * 格式："yyyy-MM-dd HH:mm"
         */
        ONLY_MINUTE {
            public String getValue() {
                return "yyyy-MM-dd HH:mm";
            }
        },
        /**
         * 格式："MM-dd"
         */
        ONLY_MONTH_DAY {
            public String getValue() {
                return "MM-dd";
            }
        },
        /**
         * 格式："MM-dd HH:mm"
         */
        ONLY_MONTH_SEC {
            public String getValue() {
                return "MM-dd HH:mm";
            }
        },
        /**
         * 格式："HH:mm:ss"
         */
        ONLY_TIME {
            public String getValue() {
                return "HH:mm:ss";
            }
        },
        /**
         * 格式："HH:mm"
         */
        ONLY_HOUR_MINUTE {
            public String getValue() {
                return "HH:mm";
            }
        };
        /**
         * 格式："MMM d, yyyy h:m:s aa"
         */

        /**
         * 格式："MMM d, yyyy HH:mm"
         */
//        ONLY_ENGLISH_MINUTE{public String getValue(){return "MMM d, yyyy HH:mm";}};

        /**
         * 格式："EEE MMM d HH:mm:ss ‘CST’ yyyy"
         */
        public abstract String getValue();
    }

    /**
     * 获取当前时间
     *
     * @return 返回当前时间，格式2017-05-04	10:54:21
     */
    public static String getNowDate(DatePattern pattern) {
        String dateString = null;
        Calendar calendar = Calendar.getInstance();
        Date dateNow = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern.getValue(), Locale.CHINA);
        dateString = sdf.format(dateNow);
        return dateString;
    }

    /**
     * 将一个日期字符串转换成Data对象
     *
     * @param dateString 日期字符串
     * @param pattern    转换格式
     * @return 返回转换后的日期对象
     */
    public static Date stringToDate(String dateString, DatePattern pattern) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern.getValue(), Locale.CHINA);
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将一个日期字符串转换成Data对象
     *
     * @param dateString 日期字符串
     * @return 返回转换后的日期对象
     */
    public static Date stringToEnglishDate(String dateString) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy HH:mm", Locale.ENGLISH);
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String get18String(Date date,DatePattern pattern){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -18);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//        String dateStr = sdf.format(calendar.getTime());

        String string = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern.getValue(), Locale.CHINA);
        string = sdf.format(calendar.getTime());
        return string;

//        DateFormat.format("yyyy-MM-dd", calendar).toString();

    }

    /**
     * 将date转换成字符串
     *
     * @param date    日期
     * @param pattern 日期的目标格式
     * @return
     */
    public static String dateToString(Date date, DatePattern pattern) {
        String string = "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern.getValue(), Locale.CHINA);
        string = sdf.format(date);
        return string;
    }

    /**
     * 获取指定日期周几
     *
     * @param date 指定日期
     * @return 返回值为： "周日", "周一", "周二", "周三", "周四", "周五", "周六"
     */
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0)
            week = 0;
        return weekDays[week];
    }

    /**
     * 获取指定日期对应周几的序列
     *
     * @param date 指定日期
     * @return 周一：1	周二：2	周三：3	周四：4	周五：5	周六：6	周日：7
     */
    public static int getIndexWeekOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int index = calendar.get(Calendar.DAY_OF_WEEK);
        if (index == 1) {
            return 7;
        } else {
            return --index;
        }
    }

    /**
     * 返回当前月份
     *
     * @return
     */
    public static int getNowMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前月号
     *
     * @return
     */
    public static int getNowDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getNowYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取本月份的天数
     *
     * @return
     */
    public static int getNowDaysOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return daysOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.DATE) + 1);
    }

    /**
     * 获取指定月份的天数
     *
     * @param year  年份
     * @param month 月份
     * @return 对应天数
     */
    public static int daysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if ((year % 4 == 0 && year % 100 == 0) || year % 400 != 0) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    //将中文格式转换成英文格式 yyyy-MM-ddTHH:mm:ss
    public static String CN_Changeto_English(String strData) {
        StringBuilder result = new StringBuilder();
        String year = "";
        String month = "";
        String day = "";
        String time = "";
        String Hour = "";
        String Minute = "";

        String[] timeStr = strData.split("-");
        for (int i = timeStr.length - 1; i >= 0; i--) {
            if (i == 2) {
                String[] Day_and_time = timeStr[i].split("T");
                day = Day_and_time[0];
                String[] Hour_and_minute = Day_and_time[1].split(":");
                Hour = Hour_and_minute[0];
                Minute = Hour_and_minute[1];
            } else if (i == 1) {
                switch (timeStr[i]) {
                    case "01":
                        month = "January";
                        break;
                    case "02":
                        month = "February";
                        break;
                    case "03":
                        month = "March";
                        break;
                    case "04":
                        month = "April";
                        break;
                    case "05":
                        month = "May";
                        break;
                    case "06":
                        month = "June";
                        break;
                    case "07":
                        month = "July";
                        break;
                    case "08":
                        month = "August";
                        break;
                    case "09":
                        month = "September";
                        break;
                    case "10":
                        month = "October";
                        break;
                    case "11":
                        month = "November";
                        break;
                    case "12":
                        month = "December";
                        break;
                }
            } else if (i == 0) {
                year = timeStr[i];
            }
        }
        result.append(month).append(" ").append(day).append(",").append(year).append(",").append(Hour).append(":").append(Minute);
        return result.toString();
    }

    //将中文格式转换成英文格式 yyyy-MM-dd HH:mm:ss
    public static String CN_Changeto_English2(long currentTime, String formatType) {
        String strData = "";
        try {
            strData = longToString(currentTime, formatType);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        StringBuilder result = new StringBuilder();
        String year = "";
        String month = "";
        String day = "";
        String time = "";
        String Hour = "";
        String Minute = "";

        String[] timeStr = strData.split("-");
        for (int i = timeStr.length - 1; i >= 0; i--) {
            if (i == 2) {
                String[] Day_and_time = timeStr[i].split(" ");
                day = Day_and_time[0];
                String[] Hour_and_minute = Day_and_time[1].split(":");
                Hour = Hour_and_minute[0];
                Minute = Hour_and_minute[1];
            } else if (i == 1) {
                switch (timeStr[i]) {
                    case "01":
                        month = "January";
                        break;
                    case "02":
                        month = "February";
                        break;
                    case "03":
                        month = "March";
                        break;
                    case "04":
                        month = "April";
                        break;
                    case "05":
                        month = "May";
                        break;
                    case "06":
                        month = "June";
                        break;
                    case "07":
                        month = "July";
                        break;
                    case "08":
                        month = "August";
                        break;
                    case "09":
                        month = "September";
                        break;
                    case "10":
                        month = "October";
                        break;
                    case "11":
                        month = "November";
                        break;
                    case "12":
                        month = "December";
                        break;
                }
            } else if (i == 0) {
                year = timeStr[i];
            }
        }
        result.append(month).append(" ").append(day).append(",").append(year).append(",").append(Hour).append(":").append(Minute);
        return result.toString();
    }

    public static String CN_Changeto_English3(long currentTime, String formatType) {
        String strData = "";
        try {
            strData = longToString(currentTime, formatType);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        StringBuilder result = new StringBuilder();
        String year = "";
        String month = "";
        String day = "";
        String time = "";
        String Hour = "";
        String Minute = "";

        String[] timeStr = strData.split("-");
        for (int i = timeStr.length - 1; i >= 0; i--) {
            if (i == 2) {
                String[] Day_and_time = timeStr[i].split(" ");
                day = Day_and_time[0];
                String[] Hour_and_minute = Day_and_time[1].split(":");
                Hour = Hour_and_minute[0];
                Minute = Hour_and_minute[1];
            } else if (i == 1) {
                switch (timeStr[i]) {
                    case "01":
                        month = "January";
                        break;
                    case "02":
                        month = "February";
                        break;
                    case "03":
                        month = "March";
                        break;
                    case "04":
                        month = "April";
                        break;
                    case "05":
                        month = "May";
                        break;
                    case "06":
                        month = "June";
                        break;
                    case "07":
                        month = "July";
                        break;
                    case "08":
                        month = "August";
                        break;
                    case "09":
                        month = "September";
                        break;
                    case "10":
                        month = "October";
                        break;
                    case "11":
                        month = "November";
                        break;
                    case "12":
                        month = "December";
                        break;
                }
            } else if (i == 0) {
                year = timeStr[i];
            }
        }
        result.append(month).append(" ").append(day).append(",").append(year);
        return result.toString();
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    public static String long2Str(long dTime) {
        return long2Str(String.valueOf(dTime), "MM/dd/yyyy");
    }

    public static String long2Str(String dTime) {
        return long2Str(dTime, "MMM d.yyyy");
    }

    public static String long2Str(long dTime, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        dTime = dTime <= 0 ? new Date().getTime() : dTime;
        String time = dateFormat.format(new Date(dTime));
        return time;
    }

    public static String long2Str(String dTime, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        long sTime = TextUtils.isEmpty(dTime) ? new Date().getTime() : Long.parseLong(dTime);
        String time = dateFormat.format(new Date(sTime));
        return time;
    }

    /**
     * 获取两个时间差
     *
     * @param longStr
     * @return 天数
     */
    public static String getDiffTimeStr(String longStr) {
        if (TextUtils.isEmpty(longStr)) return "0";
        return String.valueOf(getDiffTime(longStr));
    }

    /**
     * 获取两个时间差
     *
     * @param longStr
     * @return 天数
     */
    public static long getDiffTime(String longStr) {
        longStr = StringUtils.strNoNull(longStr);
        Date fromDate = new Date(System.currentTimeMillis());
        //PROCESSING
        Date toDate = new Date(Long.valueOf(longStr));
        return dayComparePrecise(fromDate, toDate);
    }

    /**
     * 计算2个日期之间相差的  相差多少年月日
     * 比如：2011-02-02 到  2017-03-02 相差 6年，1个月，0天
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static long dayComparePrecise(Date fromDate, Date toDate) {
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to = Calendar.getInstance();
        to.setTime(toDate);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int day = toDay - fromDay;
        return day;
    }

    /**
     * 计算2个日期之间相差的  相差多少年月日
     * 比如：2011-02-02 到  2017-03-02 相差 6年，1个月，0天
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static long yearComparePrecise(Date fromDate, Date toDate) {
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to = Calendar.getInstance();
        to.setTime(toDate);
        int fromYear = from.get(Calendar.YEAR);
        int toYear = to.get(Calendar.YEAR);
        int year = toYear - fromYear;
        return year;
    }

    /**
     * 计算2个日期之间相差的  相差多少年月日
     * 比如：2011-02-02 到  2017-03-02 相差 6年，1个月，0天
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public static long monthComparePrecise(Date fromDate, Date toDate) {
        Calendar from = Calendar.getInstance();
        from.setTime(fromDate);
        Calendar to = Calendar.getInstance();
        to.setTime(toDate);
        int fromMonth = from.get(Calendar.MONTH);
        int toMonth = to.get(Calendar.MONTH);
        int month = toMonth - fromMonth;
        return month;
    }

    public static String getMothAndDay(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat(DatePattern.ALL_TIME.getValue());
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 这里要注意，月份是从0开始。
        int month = calendar.get(Calendar.MONTH);
        // 获取天
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String monthStr = "";
        String dayStr = "";
        if ((month + 1) < 10) {
            monthStr = "0" + (month + 1);
        } else {
            monthStr = (month + 1) + "";
        }

        if (day < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = day + "";
        }

        return monthStr + dayStr;

    }

    /**
     * 获取月份
     * @param dateStr
     * @param pattern
     * @return
     */
    public static int getMonth(String dateStr, DatePattern pattern) {
        int month = 0;
        SimpleDateFormat format = new SimpleDateFormat(pattern.getValue());

        try {
            Date date = format.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            month = calendar.get(Calendar.MONTH);
            return month + 1;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return month;
    }

    /**
     * 获取指定日期天
     * @param dateStr
     * @param pattern
     * @return
     */
    public static int getDay(String dateStr, DatePattern pattern) {
        int day = 0;
        SimpleDateFormat format = new SimpleDateFormat(pattern.getValue());

        try {
            Date date = format.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            return day;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return day;
    }
}