package com.oceanai.test;

import com.oceanai.util.DAO;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * .
 *
 * @author Xiong Raorao
 * @since 2018-06-27-14:21
 */
public class DAOTest {

  public static void main(String[] args) throws ParseException {
    DAO dao = new DAO();
    String sql = "insert into t_device(owner, boardSn, macAdd, hddSn, privateKey, publicKey, validDate) VALUES (?,?,?,?,?,?,?)";
    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-09-17 20:27:00");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date2 = dateFormat.parse("2018-09-07");

    dao.update(sql, "test4", "hh", "hh", "hh", "privateKey", "publickey",
        new Timestamp(date2.getTime()));

  }
}
