package com.easy.auth.infrastructure.config.redis.utils;

import com.alibaba.druid.util.StringUtils;
import com.easy.auth.common.SysConstans;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java 通用工具类
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public class JavaUtil {

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
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

    private static final String CONFIG_RESOURCE_MANAGER = "application.properties";

    public static String getOrderIdByUUId() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) { // 有可能是负数
            hashCodeV = -hashCodeV;
        }
        //         0 代表前面补充0
        //         4 代表长度为4
        //         d 代表参数为正数型
        return 1 + String.format("%012d", hashCodeV);
    }

    /**
     * 校验密码是否符合格式
     *
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        String check = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{5,18}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(password);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean stringIsNull(String str) {
        if (str != null && !"".equals(str.replaceAll(" ", ""))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean stringIsNotNull(String str) {
        if (str != null && !"".equals(str.replaceAll(" ", ""))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断list是否不为空
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T extends Object> boolean listIsNotEmpty(List<T> list) {
        return list != null && list.size() > 0;
    }

    /**
     * 判断list是否为空
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T extends Object> boolean listIsEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 根据指定的日期格式生成日期时间串
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 根据指定的日期格式把日期时间串转为date
     *
     * @param date
     * @param format
     * @return
     */
    public static Date stringToDate(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date da = null;
        try {
            da = dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return da;
    }

    /**
     * 字符串日期转为date
     *
     * @param dateStr
     * @return
     */
    public static Date stringToFormatDate(String dateStr) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = null;
        boolean dateFlag = true;
        try {
            date = sdf1.parse(dateStr);
            dateFlag = true;
        } catch (Exception e1) {
            try {
                date = sdf2.parse(dateStr);
                dateFlag = true;
            } catch (Exception e2) {
                try {
                    date = sdf3.parse(dateStr);
                    dateFlag = true;
                } catch (Exception e3) {
                    try {
                        date = sdf4.parse(dateStr);
                    } catch (Exception e4) {
                        e4.printStackTrace();
                        date = null;
                    }
                }
            }
        }
        return date;
    }

    /**
     * 根据key读取配置文件value
     *
     * @param key
     * @return
     */
    public static String getPropertiesValueByKey(String key) {
        String propertiesValue = "";
        try {
            Properties properties = new Properties();
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            InputStream in = JavaUtil.class.getClassLoader().getResourceAsStream(CONFIG_RESOURCE_MANAGER);
            // 使用properties对象加载输入流
            properties.load(in);
            // 获取key对应的value值
            propertiesValue = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propertiesValue;
    }

    /**
     * 校验日期是否合法
     *
     * @param date
     * @return
     */
    public static boolean isDate(String date) {
        String rexp =
                "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                        + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))"
                        + "[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        return Pattern.matches(rexp, date);
    }

    /**
     * 用户手机验证码校验
     *
     * @param phoneNo
     * @param userCode
     * @return
     */
    public static boolean checkPhoneValidCode(String phoneNo, String userCode) {
        try {
            Jedis jedis = RedisUtil.createRedis();
            String userValidCode = jedis.get(SysConstans.PHONE_CODE_CACHE + phoneNo);
            jedis.close();
            if (userValidCode != null && userValidCode.length() == 6) {
                if (!userValidCode.equals(userCode)) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 校验手机号是否合法
     *
     * @param phone
     * @return
     */
    public static boolean isPhoneNo(String phone) {
        // 手机号验证
        if (JavaUtil.stringIsNull(phone)) {
            return false;
        } else if (!JavaUtil.isMobilePhone(phone)) {
            return false;
        }
        return true;
    }

    /**
     * 校验手机号是否合法
     *
     * @param str
     * @return
     */
    public static boolean isMobilePhone(String str) {
        // 验证手机号
        String regex = "^[1][3,4,5,7,8,9][0-9]{9}$";
        return Pattern.matches(regex, str);
    }

    /**
     * 判断是否为合法数字
     *
     * @param num
     * @return
     */
    public static boolean isNumber(String num) {
        int index1 = num.indexOf(".");
        int index2 = num.indexOf("0");
        Float number = null;
        boolean numFlag = true;
        if (index1 > 1 && index2 == 0) {
            numFlag = false;
        } else if (index1 == -1 && num.length() > 1 && index2 == 0) {
            numFlag = false;
        }
        try {
            number = Float.parseFloat(num);
            if (number < 0) {
                numFlag = false;
            }
        } catch (Exception e) {
            numFlag = false;
        }
        return numFlag;
    }

    /**
     * 获取年份
     *
     * @param date
     * @return
     */
    public static Integer getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static Integer getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 工号获取主键id策略
     *
     * @param phoneNo
     * @return
     */
    public static String generateLoginNo(String phoneNo) {

        return System.currentTimeMillis() / 1000 + phoneNo.substring(7, 11);
    }

    /**
     * 用户注册获取主键id策略
     *
     * @param
     * @return
     */
    public static String generateId() {

        return System.currentTimeMillis() / 1000 + "";
    }

    /**
     * 获取幼儿年龄
     *
     * @return
     */
    public static Integer getAge(Date birthDate, String healthDate) {
        // 计算年龄
        Integer birthdayYear = getYear(birthDate);
        Integer birthdayMonth = getMonth(birthDate);
        // Date date = new Date();
        Date createDate = null;
        if (stringIsNull(healthDate)) {
            createDate = new Date();
        } else {
            createDate = stringToFormatDate(healthDate);
        }
        Integer nowYear = getYear(createDate);
        Integer nowMonth = getMonth(createDate) - birthdayMonth;
        Integer age = nowYear - birthdayYear;
        age = nowMonth >= 0 ? age : age - 1;
        if (age != null && age > 6) {
            age = 6;
        }

        if (age != null && age < 3) {
            age = 3;
        }
        return age;
    }

    /**
     * map转对象
     *
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null) {
            return null;
        }

        Object obj = beanClass.newInstance();

        org.apache.commons.beanutils.BeanUtils.populate(obj, map);

        return obj;
    }

    /**
     * 对象装map
     *
     * @param obj
     * @return
     */
    public static Map<?, ?> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }

        return new org.apache.commons.beanutils.BeanMap(obj);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void main(String[] args) {

        Calendar date = Calendar.getInstance();
        Long time = date.getTimeInMillis();
        //        logger.debug("ssss" + System.currentTimeMillis() / 1000);

        //        logger.debug("13412345678".substring(7, 11));
        //        logger.debug(getUUID());

    }

    public static void setCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            String name,
            String value,
            int seconds) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
            return;
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(seconds);
        cookie.setPath("/");
        response.setHeader(
                "P3P", "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");
        response.addCookie(cookie);
    }

    public static String getCookieValue(String name) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (name.equalsIgnoreCase(cookies[i].getName())) {
                    return cookies[i].getValue();
                }
            }
        }
        return "";
    }

    /**
     * 获取本机ip
     *
     * @return
     */
    public static String getLocalHost() {
        try {
            // 获得本机IP
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "";
        }
    }
}
