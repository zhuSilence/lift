package com.silence.mini.program.life.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
    private static final String src_number = "0123456789";
    private static final String src_lower = "abcdefghijklmnopqrstuvwxyz";
    private static final String src_upper = src_lower.toUpperCase();
    private static final String src_hex_lower = "0123456789abcdef";
    private static final String src_hex_upper = src_hex_lower.toUpperCase();
    private static final String esc_char = "?";

    public static String get(int size) {
        StringBuffer r = new StringBuffer(size);
        String src = src_number + src_upper;
        for (int i = 0; i < size; i++) {
            r.append(getRandomChar(src));
        }
        return r.toString();
    }

    public static String get(String format) {
        StringBuffer r = new StringBuffer(format.length());
        String src = src_number + src_upper;
        for (int i = 0; i < format.length(); i++) {
            String curr = String.valueOf(format.charAt(i));
            if (curr.equalsIgnoreCase(esc_char)) {
                r.append(getRandomChar(src));
            } else {
                r.append(curr);
            }
        }
        return r.toString();
    }

    public static String get(String format, char esc) {
        StringBuffer r = new StringBuffer(format.length());
        String src = src_number + src_upper;
        for (int i = 0; i < format.length(); i++) {
            String curr = String.valueOf(format.charAt(i));
            if (curr.equalsIgnoreCase(String.valueOf(esc))) {
                r.append(getRandomChar(src));
            } else {
                r.append(curr);
            }
        }
        return r.toString();
    }

    public static String getNum(int size) {
        StringBuffer r = new StringBuffer(size);
        String src = src_number;
        for (int i = 0; i < size; i++) {
            r.append(getRandomChar(src));
        }
        return r.toString();
    }

    public static String getNum(String format) {
        StringBuffer r = new StringBuffer(format.length());
        String src = src_number;
        for (int i = 0; i < format.length(); i++) {
            String curr = String.valueOf(format.charAt(i));
            if (curr.equalsIgnoreCase(esc_char)) {
                r.append(getRandomChar(src));
            } else {
                r.append(curr);
            }
        }
        return r.toString();
    }

    public static String getNum(String format, char esc) {
        StringBuffer r = new StringBuffer(format.length());
        String src = src_number;
        for (int i = 0; i < format.length(); i++) {
            String curr = String.valueOf(format.charAt(i));
            if (curr.equalsIgnoreCase(String.valueOf(esc))) {
                r.append(getRandomChar(src));
            } else {
                r.append(curr);
            }
        }
        return r.toString();
    }

    public static String getHex(int size) {
        StringBuffer r = new StringBuffer(size);
        String src = src_hex_upper;
        for (int i = 0; i < size; i++) {
            r.append(getRandomChar(src));
        }
        return r.toString();
    }

    public static String getHex(String format) {
        StringBuffer r = new StringBuffer(format.length());
        String src = src_hex_upper;
        for (int i = 0; i < format.length(); i++) {
            String curr = String.valueOf(format.charAt(i));
            if (curr.equalsIgnoreCase(esc_char)) {
                r.append(getRandomChar(src));
            } else {
                r.append(curr);
            }
        }
        return r.toString();
    }

    public static String getHex(String format, char esc) {
        StringBuffer r = new StringBuffer(format.length());
        String src = src_hex_upper;
        for (int i = 0; i < format.length(); i++) {
            String curr = String.valueOf(format.charAt(i));
            if (curr.equalsIgnoreCase(String.valueOf(esc))) {
                r.append(getRandomChar(src));
            } else {
                r.append(curr);
            }
        }
        return r.toString();
    }

    /**
     * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
     *
     * @param sourceDate
     * @param formatLength
     * @return 重组后的数据
     */
    public static String frontCompWithZore(int sourceDate, int formatLength) {
        String newString = String.format("%0" + formatLength + "d", sourceDate);
        return newString;
    }

    /**
     * 生成小于指定数的随机数
     *
     * @param maxVal
     * @return
     */
    public static Integer generateLtInt(int maxVal) {

        //return (int) (Math.random() * maxVal);
        return ThreadLocalRandom.current().nextInt(maxVal);
    }

    /**
     * 生成介于二个数之间的随机数
     *
     * @param minVal
     * @param maxVal
     * @return
     */
    public static Long generateLtInt(int minVal, int maxVal) {

        return Math.round(Math.random() * (maxVal - minVal) + minVal);
    }

    public static Long generateLtInt(long minVal, long maxVal) {

        return Math.round(Math.random() * (maxVal - minVal) + minVal);
    }

    private static final String getRandomChar(String src) {
        if (null == src || "".equals(src)) {
            return "";
        }
        return String.valueOf(src.charAt(ThreadLocalRandom.current().nextInt(src.length())));
    }

    public static void main(String[] args) {
        //产生4位的，由数字和大写字母组成的随机字符串；
        String s1 = RandomUtils.get(4);
        System.out.println(s1);

        //产生5位的，由数字组成的随机字符串；
        String s2 = RandomUtils.getNum(10);
        System.out.println(s2);

        //产生6位的，由十六进制字符组成的随机字符串；
        String s3 = RandomUtils.getHex(6);
        System.out.println(s3);

        //产生如下格式的随机字符串“{E3-K91Z}”，“?”表示一个占位符；
        String s4 = RandomUtils.get("{??-????}");
        System.out.println(s4);

        //产生如下格式的随机字符串“Are you W5B?”，可以自己指定临时占位符，这里用“#”；
        String s5 = RandomUtils.get("Are you ###?", '#');
        System.out.println(s5);

        for (int i = 0; i < 50; i++) {
            System.out.println(generateLtInt(21));
        }

    }
}
