package com.easy.auth.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

/**
 * 汉字转换为拼音
 *
 * <p>@Author: 连晋
 *
 * <p>@Description:
 *
 * <p>@email: 832192@qq.com
 *
 * <p>@Source: Created with IntelliJ IDEA.
 */
public class PinYin {
    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PinYin.class);

    /**
     * 测试main方法
     *
     * @param args
     */
    public static void main(String[] args) {
        String string = "李娅琳";
        logger.debug(toFirstChar("雷飞宏").toUpperCase()); // 转为首字母大写
        logger.debug(toPinyin(string));
        logger.debug(stringToAscii("LFH"));
    }

    /**
     * 获取字符串拼音的第一个字母
     *
     * @param chinese
     * @return
     * @author leifeihong
     */
    public static String toFirstChar(String chinese) {

        if (StringUtils.isBlank(chinese)) {
            return "";
        }

        StringBuilder pinyinStr = new StringBuilder();
        // 转为单个字符
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : newChar) {
            if (c > 128) {
                try {
                    pinyinStr.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinStr.append(c);
            }
        }
        return pinyinStr.toString();
    }

    /**
     * 汉字转为拼音
     *
     * @param chinese
     * @return
     * @author leifeihong
     */
    public static String toPinyin(String chinese) {
        if (StringUtils.isBlank(chinese)) {
            return null;
        }

        StringBuilder pinyinStr = new StringBuilder();
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : newChar) {
            if (c > 128) {
                try {
                    pinyinStr.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinStr.append(c);
            }
        }
        return pinyinStr.toString();
    }

    /**
     * 将字符串转成ASCII
     *
     * @param value
     * @return
     * @author leifeihong
     */
    public static String stringToAscii(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        StringBuilder sbu = new StringBuilder();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]);
                // 添加逗号
                // 				sbu.append((int)chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }
}
