package com.chy.seckill_demo.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: chy
 * @Date: 2022/4/15 22:39
 * @Description:
 */
public class MD5Util {
    private static final String salt = "1a2b3c4d";
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassToFromPass(String inputPass) {
        String str = "" + "1a2b3c4d".charAt(0) + "1a2b3c4d".charAt(2) + inputPass + "1a2b3c4d".charAt(5) + "1a2b3c4d".charAt(4);
        return md5(str);
    }

    public static String fromPassToDBPass(String fromPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + fromPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String salt) {
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = fromPassToDBPass(fromPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(fromPassToDBPass("d3b1294a61a07da9b49b6e22b2cbd7f9", "1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456", "1a2b3c4d"));
    }
}
