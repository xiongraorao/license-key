package com.oceanai.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.security.provider.SHA;

/**
 * sha256 工具类.
 *
 * @author Xiong Raorao
 * @since 2018-06-27-18:38
 */
public class SHA256Util {

  public static void main(String[] args) {
    String ss = "hello";
    System.out.println(getSHA256StrJava(ss));
  }

  /**
   * 利用java原生的摘要实现SHA256加密
   *
   * @param str 加密后的报文
   */
  public static String getSHA256StrJava(String str) {
    MessageDigest messageDigest;
    String encodeStr = "";
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
      messageDigest.update(str.getBytes("UTF-8"));
      encodeStr = byte2Hex(messageDigest.digest());
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return encodeStr;
  }

  /**
   * 将byte转为16进制
   */
  private static String byte2Hex(byte[] bytes) {
    StringBuffer stringBuffer = new StringBuffer();
    String temp = null;
    for (int i = 0; i < bytes.length; i++) {
      temp = Integer.toHexString(bytes[i] & 0xFF);
      if (temp.length() == 1) {
        //1得到一位的进行补0操作
        stringBuffer.append("0");
      }
      stringBuffer.append(temp);
    }
    return stringBuffer.toString();
  }
}


