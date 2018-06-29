package com.oceanai.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oceanai.bean.DeviceInfo;
import com.oceanai.bean.LicenseInfo;
import com.oceanai.util.DAO;
import com.oceanai.util.HttpUtil;
import com.oceanai.util.RSATool;
import com.oceanai.util.SHA256Util;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 生成授权码.
 *
 * @author Xiong Raorao
 * @since 2018-06-25-15:14
 */
public class GenerateServlet extends HttpServlet {


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String raw = HttpUtil.readRequestData(req);
    System.out.println("raw data: " + raw);
    //Gson gson = new Gson();
    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    DeviceInfo deviceInfo = gson.fromJson(raw, DeviceInfo.class);
    RSATool rsaTool = RSATool.getInstance();
    String publicKey = rsaTool.getPublicKeyBase64();
    String privateKey = rsaTool.getPrivateKeyBase64();

    // raw 做sha256 加密
    String sha256 = SHA256Util.getSHA256StrJava(raw);

    // 使用私钥加密
    String licenseCode = rsaTool.encrypt(sha256);

    // 写入数据库
    DAO dao = new DAO();
    String sql =
        "insert into t_device(owner, boardSn, macAdd, hddSn, privateKey, publicKey, validDate, sha256) "
            + "VALUES (?,?,?,?,?,?,?,?)";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date validDate = null;
    try {
      validDate = dateFormat.parse(deviceInfo.validDate);
    } catch (ParseException e) {
      e.printStackTrace();
      return;
    }
    int count = dao
        .update(sql, deviceInfo.owner, deviceInfo.boardSn, deviceInfo.macAdd, deviceInfo.hddSn,
            privateKey, publicKey, validDate, sha256);
    if (count > 0) {
      System.out.println("updated " + count + " row success!");
    }

    try {
      //返回数据
      LicenseInfo licenseInfo = new LicenseInfo(deviceInfo.owner, publicKey,
          deviceInfo.validDate, licenseCode);
      response(resp, gson.toJson(licenseInfo));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void response(HttpServletResponse resp, String result)
      throws Exception {
    resp.setContentType("application/json;charset=UTF-8");
    byte[] bytes = (result).getBytes("UTF-8");
    resp.getOutputStream().write(bytes);
    resp.getOutputStream().flush();
  }
}
