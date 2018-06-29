package com.oceanai.test;

import com.google.gson.Gson;
import com.oceanai.bean.LicenseKey;
import com.oceanai.util.RSATool;
import com.oceanai.util.SHA256Util;

/**
 * .
 *
 * @author Xiong Raorao
 * @since 2018-06-27-15:09
 */
public class RSAToolTest {

  public static void main(String[] args) {
    RSATool rsaTool = RSATool.getInstance();
    System.out.println(rsaTool.getPublicKeyBase64().length());
    System.out.println(rsaTool.getPublicKeyBase64().length());

    // 加密一段字符串
    String ss = "hello";
    String encryptedStr = rsaTool.encrypt(ss);
    String publicKey = rsaTool.getPublicKeyBase64();
    System.out.println("1. encryptedStr: ->" + encryptedStr);
    System.out.println("1. publicKey: ->" + publicKey);

    // 加密sha和时间
    String sha256 = SHA256Util.getSHA256StrJava("hello");
    String date = "2019-08-09";
    LicenseKey key = new LicenseKey(sha256, date);
    String encrypted =  rsaTool.encrypt(new Gson().toJson(key));
    System.out.println(rsaTool.decrypt(encrypted));
  }

}
