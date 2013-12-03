package com.rootls.base.util;

import java.security.MessageDigest;

/**
 * @className:MD5Utils
 * @classDescription:
 * @author:luowei
 * @createTime:12-6-12
 */
public class MD5Utils {




    /**
     * 获得MD5加密密码的方法
     */
    public static String md5Hex(String origString) {
        String origMD5 = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] result = md5.digest(origString.getBytes());
            origMD5 = byteArray2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return origMD5;
    }
    /**
     * 处理字节数组得到MD5密码的方法
     */
    private static String byteArray2HexStr(byte[] bs) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bs) {
            sb.append(byte2HexStr(b));
        }
        return sb.toString();
    }
    /**
     * 字节标准移位转十六进制方法
     */
    private static String byte2HexStr(byte b) {
        String hexStr = null;
        int n = b;
        if (n < 0) {
            //若需要自定义加密,请修改这个移位算法即可
            n = b & 0x7F + 128;
        }
        hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);
        return hexStr.toUpperCase();
    }
    /**
     * 提供一个MD5多次加密方法
     */
    public static String getMD5ofStr(String origString, int times) {
        String md5 = md5Hex(origString);
        for (int i = 0; i < times - 1; i++) {
            md5 = md5Hex(md5);
        }
        return md5Hex(md5);
    }
    /**
     * 密码验证方法
     */
    public static boolean verifyPassword(String inputStr, String MD5Code) {
        return md5Hex(inputStr).equals(MD5Code);
    }

    /**
     * 重载一个多次加密时的密码验证方法
     */
    public static boolean verifyPassword(String inputStr, String MD5Code, int times) {
        return getMD5ofStr(inputStr, times).equals(MD5Code);
    }
    /**
     * 提供一个测试的主函数
     */
    public static void main(String[] args) {
        System.out.println("123:" + md5Hex("123"));
        System.out.println("123456789:" + md5Hex("123456789"));
        System.out.println("sarin:" + md5Hex("sarin"));
        System.out.println("123:" + getMD5ofStr("123", 4));
    }
}

