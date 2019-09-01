package com.easy.auth.utils;

public class RandomUtils {

    /**
     * 指定范围内随机返回一个数
     *
     * @param start
     * @param end
     * @return
     */
    public static int randomNumber(int start, int end) {
        int ran2 = (int) (Math.random() * (end - start) + start);
        return ran2;
    }

    /**
     * 获取指定长度内的数值中的一个随机数  范围为 10*length 到  10*length*9
     *
     * @param length
     * @return
     */
    public static int randomSpecifiedLengthNumber(int length) {
        if (length == 0) {
            return length;
        } else {
            int start = (int) Math.pow(10, length);
            int end = start * 9;
            return randomNumber(start, end);
        }

    }
}
