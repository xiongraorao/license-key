package com.oceanai.servlet;

import com.google.gson.JsonObject;
import com.oceanai.util.DAO;
import com.oceanai.util.RSATool;
import java.util.UUID;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * .
 *
 * @author Xiong Raorao
 * @since 2018-11-27-15:15
 */
public class GenServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    RSATool tool = RSATool.getInstance();
    String publicKey = tool.getPublicKeyBase64();
    String privateKey = tool.getPrivateKeyBase64();
    String id = UUID.randomUUID().toString();

    // 保存在mysql中
    DAO dao = new DAO();
    String sql = "insert into t_key(name, public, private) values (?,?,?)";
    int count = dao.update(sql, id, publicKey, privateKey);
    if (count > 0) {
      System.out.println("updated " + count + " row success!");
    } else {
      System.out.println("updated failed!");
    }

    JsonObject obj = new JsonObject();
    obj.addProperty("public_key", publicKey);
    obj.addProperty("id", id);
    try {
      response(resp, obj.toString());
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
