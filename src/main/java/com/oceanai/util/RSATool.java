package com.oceanai.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 非对称加密工具.
 *
 * @author Xiong Raorao
 * @since 2018-06-26-15:45
 */
public class RSATool {

  private Map<String, Key> keyPair;

  private RSATool() {
    this.keyPair = generateKeyPair();
  }

  public static RSATool getInstance() {
    return new RSATool();
  }

  public static void main(String[] args) {

    RSATool rsa = RSATool.getInstance();
    String base64 = rsa.getPrivateKeyBase64();
    byte[] decode = rsa.base2bytes(base64);

    String ss = "hello";
    ss = SHA256Util.getSHA256StrJava(ss);
    System.out.println("1. " + rsa.encrypt(ss));
    System.out.println("2. " + rsa.encrypt(ss, rsa.bytes2key(decode, KEY.PRIVATE)));
    //System.out.println(rsa.keyPair.get("private").getAlgorithm() + ", format: " + rsa.keyPair.get("private").getFormat());
    //System.out.println(rsa.keyPair.get("public").getAlgorithm() + ", format: " + rsa.keyPair.get("public").getFormat());
  }

  // 生成RSA 秘钥对
  private Map<String, Key> generateKeyPair() {
    /** RSA算法要求有一个可信任的随机数源 */
    SecureRandom sr = new SecureRandom();
    /** 为RSA算法创建一个KeyPairGenerator对象 */
    KeyPairGenerator kpg = null;
    try {
      kpg = KeyPairGenerator.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
    kpg.initialize(1024, sr);
    /** 生成密匙对 */
    KeyPair kp = kpg.generateKeyPair();
    /** 得到公钥 */
    Key publicKey = kp.getPublic();
    /** 得到私钥 */
    Key privateKey = kp.getPrivate();

    // 返回公钥和私钥
    Map<String, Key> result = new HashMap<>();
    result.put("public", publicKey);
    result.put("private", privateKey);
    return result;

  }

  /**
   * 保存公钥
   *
   * @param dstFilePath 输出目录或路径
   */
  public void savePublicKey(String dstFilePath) throws IOException {
    getKey(dstFilePath, KEY.PUBLIC);
  }

  public void savePrivateKey(String dstFilePath) throws IOException {
    getKey(dstFilePath, KEY.PRIVATE);
  }

  private void getKey(String dstFilePath, KEY type) throws IOException {
    File dst = new File(dstFilePath);
    ObjectOutputStream oss = null;
    if (dst.isDirectory()) {
      oss = new ObjectOutputStream(new FileOutputStream(dst + File.separator + "public.key"));
    } else {
      if (dstFilePath.endsWith(File.separator)) {
        dst.mkdir();
        oss = new ObjectOutputStream(
            new FileOutputStream(dst + File.separator + "public.key"));
      } else {
        File parent = new File(dst.getParent());
        parent.mkdir();
        oss = new ObjectOutputStream(new FileOutputStream(dst));
      }

    }
    if (type.equals(KEY.PUBLIC)) {
      oss.writeObject(keyPair.get("public"));
    } else if (type.equals(KEY.PRIVATE)) {
      oss.writeObject(keyPair.get("private"));
    }
    oss.close();
  }

  public String getPublicKeyBase64() {
    return bytes2base(keyPair.get("public").getEncoded());
  }

  public String getPrivateKeyBase64() {
    return bytes2base(keyPair.get("private").getEncoded());
  }

  // 使用私钥对字符串加密
  public String encrypt(String source) {
    return encrypt(source, keyPair.get("private"));
  }

  public String encrpyt(String source, String keyFile) {
    Key key = getKeyFromFile(keyFile);
    if (key != null) {
      return encrypt(source, key);
    } else {
      return null;
    }

  }

  public String encrypt(String source, Key key) {

    /** 得到Cipher对象来实现对源数据的RSA加密 */
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] b = source.getBytes();
      /** 执行加密操作 */
      byte[] b1 = cipher.doFinal(b);
      return bytes2base(b1);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
        | BadPaddingException | IllegalBlockSizeException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String encrypt(String source, String keyString, KEY type) {
    Key key = base2key(keyString, type);
    return encrypt(source, key);
  }

  // 使用公钥对字符串解密
  public String decrypt(String source) {
    return decrypt(source, keyPair.get("public"));
  }

  public String decrypt(String source, String keyFile) {
    Key key = getKeyFromFile(keyFile);
    if (key != null) {
      return decrypt(source, key);
    } else {
      return null;
    }

  }

  public String decrypt(String source, String keyString, KEY type) {
    Key key = base2key(keyString, type);
    return decrypt(source, key);
  }

  public String decrypt(String source, Key key) {
    /** 将文件中的私钥对象读出 */
    //ObjectInputStream ois = new ObjectInputStream(new FileInputStream("privatekey.keystore"));
    //Key key = (Key) ois.readObject();
    /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.DECRYPT_MODE, key);
      byte[] b1 = base2bytes(source);
      /** 执行解密操作 */
      byte[] b = cipher.doFinal(b1);
      return new String(b);
    } catch (NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException
        | IllegalBlockSizeException | InvalidKeyException e) {
      e.printStackTrace();
    }
    return null;
  }

  private Key getKeyFromFile(String keyFile) {
    ObjectInputStream ois;
    Key key = null;
    try {
      ois = new ObjectInputStream(new FileInputStream(keyFile));
      key = (Key) ois.readObject();
      ois.close();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return key;
  }

  private byte[] base2bytes(String base64) {
    return Base64.getDecoder().decode(base64);
  }

  private String bytes2base(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }

  private Key base2key(String keyString, KEY type) {
    byte[] bytes = base2bytes(keyString);
    return bytes2key(bytes, type);
  }

  private Key bytes2key(byte[] keyBytes, KEY type) {
    Key key = null;
    try {
      KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
      switch (type) {
        case PUBLIC:
          key = kf.generatePublic(new X509EncodedKeySpec(keyBytes));
          break;
        case PRIVATE:
          key = kf.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
          break;
      }
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      e.printStackTrace();
    }
    return key;
  }

  public enum KEY {
    PUBLIC, PRIVATE
  }


}
