package com.easy.auth.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 日期处理工具类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public class DateUtils {

  public static final SimpleDateFormat DATE_SDF_WZ = new SimpleDateFormat("yyyy年MM月dd日");

  public static String getNowYearMonthDayStr() {
    return DATE_SDF_WZ.format(new Date(System.currentTimeMillis()));
  }

  public static final int DATE_LENGTH = 10;
  /**
   * 比较两个日期的大小 大于返回1 小于返回-1，相等返回0
   *
   * @author mzw
   */
  public static int compareDate(String bDate, String eDate, String fmt) {
    if (bDate.length() <= DATE_LENGTH) {
      bDate += " 00:00:00";
    }
    if (eDate.length() <= DATE_LENGTH) {
      eDate += " 00:00:00";
    }

    SimpleDateFormat df = new SimpleDateFormat(fmt);
    try {
      Date dt1 = df.parse(bDate);
      Date dt2 = df.parse(eDate);
      if (dt1.getTime() > dt2.getTime()) {
        return 1;
      } else if (dt1.getTime() < dt2.getTime()) {
        return -1;
      } else {
        return 0;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return 0;
  }

  /**
   * 获取时间差，天数
   *
   * @author mzw
   */
  public static int getDatePoor(String bDate, String eDate) {
    if (bDate.length() <= DATE_LENGTH) {
      bDate += " 00:00:00";
    }
    if (eDate.length() <= DATE_LENGTH) {
      eDate += " 00:00:00";
    }
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Date endDate = new Date();
    Date nowDate = new Date();

    try {
      endDate = formatter.parse(eDate);
      nowDate = formatter.parse(bDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    long nd = 1000 * 24 * 60 * 60;
    long nh = 1000 * 60 * 60;
    long nm = 1000 * 60;
    // long ns = 1000;
    // 获得两个时间的毫秒时间差异
    long diff = endDate.getTime() - nowDate.getTime();
    // 计算差多少天
    long day = diff / nd;
    // 计算差多少小时
    long hour = diff % nd / nh;
    // 计算差多少分钟
    long min = diff % nd % nh / nm;
    // 计算差多少秒//输出结果
    // long sec = diff % nd % nh % nm / ns;
    return (int) day;
  }

  /**
   * 获取当前时间
   *
   * @author mzw
   */
  public static String getCurrentTime(String format) {
    String rsStr = format;
    Calendar ca = Calendar.getInstance();
    String year = GetEnoughLenStr(4, "" + ca.get(Calendar.YEAR)); // 获取年份
    String month = GetEnoughLenStr(2, "" + (ca.get(Calendar.MONTH) + 1)); // 获取月份
    String day = GetEnoughLenStr(2, "" + ca.get(Calendar.DATE)); // 获取日
    String minute = GetEnoughLenStr(2, "" + ca.get(Calendar.MINUTE)); // 分
    String hour = GetEnoughLenStr(2, "" + ca.get(Calendar.HOUR_OF_DAY)); // 小时
    String second = GetEnoughLenStr(2, "" + ca.get(Calendar.SECOND)); // 秒
    String WeekOfYear = GetEnoughLenStr(2, "" + ca.get(Calendar.DAY_OF_WEEK));
    rsStr = rsStr.replace("yyyy", year + "");
    rsStr = rsStr.replace("MM", month + "");
    rsStr = rsStr.replace("dd", day + "");
    rsStr = rsStr.replace("ww", WeekOfYear + "");
    rsStr = rsStr.replace("hh", hour + "");
    rsStr = rsStr.replace("HH", hour + "");
    rsStr = rsStr.replace("mm", minute + "");
    rsStr = rsStr.replace("ss", second + "");
    return rsStr;
  }

  public static String GetEnoughLenStr(int num, String basestr) {
    String rs = basestr;
    int baseint = rs.length();
    if (baseint < num) {
      for (int i = baseint; i < num; i++) {
        rs = "0" + rs;
      }
    }
    return rs;
  }

  /**
   * @author ao
   * @param da
   * @return 格式化日期为：yyyy-MM-dd
   */
  public static Date DateFormats(Date da) {
    /*LocalDate localDate=da.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return java.sql.Date.valueOf(localDate);*/
    if (da == null) {
      return null;
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(da);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    return calendar.getTime();
  }

  /**
   * @author ao
   * @param date
   * @param temp
   * @return 格式化日期,返回字符串
   */
  public static String dateFormatToStr(Date date, String temp) {
    SimpleDateFormat format = new SimpleDateFormat(temp);

    String date2 = null;
    try {
      date2 = format.format(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date2;
  }

  /**
   * @author ao
   * @param date
   * @param temp
   * @return 格式化日期，返回date
   */
  public static Date dateFormatToDate(String date, String temp) {
    SimpleDateFormat format = new SimpleDateFormat(temp);

    Date parse = null;
    try {
      parse = format.parse(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return parse;
  }

  public static String getBeforeAfterYear(String date, int n, String format) {
    Calendar cal = Calendar.getInstance(); // 得到一个Calendar的实例
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    if (date.equals("")) date = df.format(cal.getTime());
    try {
      cal.setTime(df.parse(date));
    } catch (ParseException e) {
      e.printStackTrace();
    }

    cal.add(Calendar.YEAR, n); // 年份+-1
    Date newdate = cal.getTime(); // 结果
    df = new SimpleDateFormat(format);
    date = df.format(newdate);
    return date;
  }

  /**
   * @author ao
   * @param year
   * @return 根据传入年份，获取双休日天数
   */
  public static Set<String> getYearDoubleWeekend(int year) {
    Set<String> listDates = new HashSet<String>();
    Calendar calendar = Calendar.getInstance(); // 当前日期
    calendar.set(year, 6, 1);
    Calendar nowyear = Calendar.getInstance();
    Calendar nexty = Calendar.getInstance();
    nowyear.set(year, 0, 1); // 2010-1-1
    nexty.set(year + 1, 0, 1); // 2011-1-1
    calendar.add(Calendar.DAY_OF_MONTH, -calendar.get(Calendar.DAY_OF_WEEK)); // 周六
    Calendar c = (Calendar) calendar.clone();
    for (;
        calendar.before(nexty) && calendar.after(nowyear);
        calendar.add(Calendar.DAY_OF_YEAR, -7)) {
      listDates.add(
          calendar.get(Calendar.YEAR)
              + "-"
              + (1 + calendar.get(Calendar.MONTH))
              + "-"
              + calendar.get(Calendar.DATE));
      listDates.add(
          calendar.get(Calendar.YEAR)
              + "-"
              + (1 + calendar.get(Calendar.MONTH))
              + "-"
              + (1 + calendar.get(Calendar.DATE)));
    }

    for (; c.before(nexty) && c.after(nowyear); c.add(Calendar.DAY_OF_YEAR, 7)) {
      listDates.add(
          c.get(Calendar.YEAR) + "-" + (1 + c.get(Calendar.MONTH)) + "-" + c.get(Calendar.DATE));
      listDates.add(
          c.get(Calendar.YEAR)
              + "-"
              + (1 + c.get(Calendar.MONTH))
              + "-"
              + (1 + c.get(Calendar.DATE)));
    }
    return listDates;
  }

  public static int countWorkDay(int year, int month) {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.YEAR, year);
    // 月份是从0开始计算，所以需要减1
    c.set(Calendar.MONTH, month - 1);

    // 当月最后一天的日期
    int max = c.getActualMaximum(Calendar.DAY_OF_MONTH);
    // 开始日期为1号
    int start = 1;
    // 计数
    int count = 0;
    while (start <= max) {
      c.set(Calendar.DAY_OF_MONTH, start);
      if (isWorkDay(c)) {
        count++;
      }
      start++;
    }
    return count;
  }

  // 判断是否工作日（未排除法定节假日，由于涉及到农历节日，处理很麻烦）
  public static boolean isWorkDay(Calendar c) {
    // 获取星期,1~7,其中1代表星期日，2代表星期一 ... 7代表星期六
    int week = c.get(Calendar.DAY_OF_WEEK);
    // 不是周六和周日的都认为是工作日
    return week != Calendar.SUNDAY && week != Calendar.SATURDAY;
  }
}
