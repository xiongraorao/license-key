package com.oceanai.util;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

/**
 * http util.
 *
 * @author Xiong Raorao
 * @since 2018-06-25-15:26
 */
public class HttpUtil {

  public static String readRequestData(HttpServletRequest request) throws IOException {
    BufferedReader br = null;
    StringBuilder sb = null;
    try {
      sb = new StringBuilder();
      br = request.getReader();
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        br.close();
      }
    }
    return sb.toString();
  }
}
