package com.ptt.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public MD5Util() {
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    @SuppressWarnings("unused")
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }



    public static void main(String[] args)throws NoSuchAlgorithmException{
        //加盐
        String salt = StringUtil.getRandomString(10);//获取指定长度的盐值
        //String salt = UUID.randomUUID().toString().toUpperCase();
        //不加盐
        String str = GetMD5Code("123");             //password
        //调用加密方法对输入的密码以及盐值进行加密，随后需要将密文以及盐值存入到数据库中
        //String md5Pwd = MD5Util.GetMD5Code(MD5Util.GetMD5Code("123")+salt);
        String md5Pwd = MD5Util.GetMD5Code(("123")+salt);
        String md5Pwd2 = MD5Util.GetMD5Code(("123")+"0a3dduk6wy");
        System.out.println(salt);
        System.out.println(str);
        System.out.println(md5Pwd);
        System.out.println(md5Pwd2);//8573de966cb23e2b9eaeca306df47fc5

        //对登录时输入的密码再加上数据库中对应的salt重新进行加密，判断加密后的密文与数据库中存储的是否相同
        //rs.getString("db14e892f1d1731a0f86cfb72f8edf3c").equals(MD5Util.GetMD5Code("123"+rs.getString("salt")));
    }
}

