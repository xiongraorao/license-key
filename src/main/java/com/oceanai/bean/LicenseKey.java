package com.oceanai.bean;

import com.google.gson.Gson;

/**
 * 加密的license-key.
 *
 * @author Xiong Raorao
 * @since 2018-06-29-10:20
 */
public class LicenseKey {

  public String sha256;
  public String date;

  public LicenseKey(String sha256, String date) {
    this.sha256 = sha256;
    this.date = date;
  }
}
