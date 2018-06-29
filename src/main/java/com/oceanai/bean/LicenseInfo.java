package com.oceanai.bean;

/**
 * 生成的证书信息.
 *
 * @author Xiong Raorao
 * @since 2018-06-27-15:52
 */
public class LicenseInfo {

  public String owner;
  public String publicKey;
  public String validDate;
  public String licenseCode;

  public LicenseInfo(String owner, String publicKey, String validDate, String licenseCode) {
    this.owner = owner;
    this.publicKey = publicKey;
    this.validDate = validDate;
    this.licenseCode = licenseCode;
  }
}
