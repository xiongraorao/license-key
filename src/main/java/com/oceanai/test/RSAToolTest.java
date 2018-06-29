package com.oceanai.test;

import com.oceanai.util.RSATool;

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
  }

}
