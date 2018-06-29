package com.oceanai.test;

import com.google.gson.Gson;
import java.util.Date;

/**
 * 测试 Gson 解析 Date 类型.
 *
 * @author Xiong Raorao
 * @since 2018-06-27-15:39
 */
public class DateTest {

  public static void main(String[] args) {
    Date date = new Date();
    A a = new A(date, "test");
    Gson gson = new Gson();
    System.out.println(gson.toJson(a));

    String raw = "{\"date\":\"2018-05-07\",\"name\":\"test\"}";
    A aa = gson.fromJson(raw, A.class);
    System.out.println(aa.date);
  }

  static class A {

    public Date date;
    public String name;

    public A(Date date, String name) {
      this.date = date;
      this.name = name;
    }
  }

}
