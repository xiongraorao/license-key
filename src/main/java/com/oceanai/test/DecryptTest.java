package com.oceanai.test;

import com.oceanai.util.RSATool;
import com.oceanai.util.RSATool.KEY;

/**
 * 公钥解密.
 *
 * @author Xiong Raorao
 * @since 2018-06-28-10:17
 */
public class DecryptTest {

  public static void main(String[] args) {
    RSATool tool = RSATool.getInstance();
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOW/dAn7Q45BAmteOMhlnx0V68pxJyk3lXwZS1HpygKI2N1p338EJhG12OMhoF9NI7nI3D8ivBcBcciiUMF7V8+FT/3cI0weuL3x9R9XPc39W57fco2EQi7UUwunEPDhIT+vG/J+QgY0YD6wCS4OKqI71qbD9yolC89sJPftbG3wIDAQAB";
    String licenseCode = "xwzOV6jZzYsJJQWfG8PIxke7CPrwQ7jck3s5ppjcGnxFP0DEnDFWbrGXfFj5DDMVGABSn28A9LtggLNMwpbsWdW8JTN4JXbc0Mv7gPIL4zSmza6NI7hZTAhyCUGICG9FKfZe0fD3kezfbrC0QxA+kDH1no9Ba0pEFE7sIW0b7Zc=";
    String result = tool.decrypt(licenseCode, publicKey, KEY.PUBLIC);
    System.out.println(result);

    publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJHjtraD43V1SUEWyLOH1p5nNy0l2quTyNqWrqXvKe4nj6+LR8HJzJI8Dr3dZAVYIVCCJN/s4X4Gq0F+b8Gs4GVJX60m2AqR+b1TAPbyGWzHiTFU4QSINUdGYtTRI4hBry81AtW+nIGQrYYIIidjkGImpPbf6pyJ35/71WZj5+4wIDAQAB";
    licenseCode = "R8Z3KzWMLuOwI9V2Ck8mDZO1/OdmdI9DT/cDrfTe5EKU7BAnF7lV9OtCza0Zh5+sQMs7ae2yIQX83xJAmUFNhCHcowhaQeEeURCPytU/DsuwsngEygq495aFtMa8/tZQJxlYofwh3RoOOnSrKuPt+Njn0n2rtERPetDtbVhjOk8=";
    result = tool.decrypt(licenseCode, publicKey, KEY.PUBLIC);
    System.out.println(result);
  }
}
