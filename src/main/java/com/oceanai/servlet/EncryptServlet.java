package com.oceanai.servlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oceanai.util.DAO;
import com.oceanai.util.HttpUtil;
import com.oceanai.util.RSATool;
import com.oceanai.util.RSATool.KEY;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * .
 *
 * @author Xiong Raorao
 * @since 2018-11-27-15:15
 */
public class EncryptServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String raw = HttpUtil.readRequestData(req);
    JsonParser parser = new JsonParser();
    JsonObject jsonObj = parser.parse(raw).getAsJsonObject();
    String id = jsonObj.get("id").getAsString();
    String content = jsonObj.get("content").getAsString();

    // 保存在mysql中
    DAO dao = new DAO();
    String sql = "select private from t_key where name = ?";
    String privateKey = dao.getForValue(sql, id);
    RSATool tool = RSATool.getInstance();

    String message = tool.encrypt(content, tool.base2key(privateKey, KEY.PRIVATE));
    JsonObject retObj = new JsonObject();
    retObj.addProperty("msg", message);
    try {
      response(resp, retObj.toString());
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
