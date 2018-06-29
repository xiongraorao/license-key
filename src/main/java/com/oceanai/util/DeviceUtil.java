package com.oceanai.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 获取设备信息的工具.
 *
 * @author Xiong Raorao
 * @since 2018-06-25-16:02
 */
public class DeviceUtil {

  public static final String CMD_NOT_FOUND = "command not found";

  public static void main(String[] args) {

  }

  /**
   * 获取linux 系统的主板信息. 需要root权限
   *
   * @return 主板序列号
   */
  public static String baseBoard() {
    String baseBoardCMD = "dmidecode -s baseboard-serial-number";
    String boardSn = null;
    boardSn = getProcessOutput(baseBoardCMD);
    return boardSn;
  }

  /**
   * 获取linux 系统的硬盘 SN.
   */
  public static String disk() {
    String diskCmd = "hdparm -I /dev/sda | grep 'Serial Number' | awk '{print $3}'";
    String result = getProcessOutput(diskCmd);
    if ( result != null && result.lastIndexOf(CMD_NOT_FOUND) >= 0) {
      System.err.println("hdparm not found");
      return null;
    } else if (result.lastIndexOf("missing") >= 0) {
      System.err.println("unknown error");
      return null;
    }
    return result;
  }

  /**
   * 获取linux 系统网卡的 MAC地址.
   */
  public static String mac() {
    return mac("eth0");
  }

  /**
   * 获取linux 系统网卡的 MAC地址.
   *
   * @param name 网卡名 默认 eth0
   */
  public static String mac(String name) {
    String result = null;
    String cmd = "ifconfig " + name + " | grep " + name + " | awk '{print $5}'";
    result = getProcessOutput(cmd);
    return result;
  }

  private static String getProcessOutput(String cmd) {
    String result = null;
    Process p;
    try {
      p = Runtime.getRuntime().exec(
          new String[] {"sh", "-c", cmd});// 管道
      BufferedReader br = new BufferedReader(new InputStreamReader(
          p.getInputStream()));
      String line;
      if ((line = br.readLine()) != null) {
        result = line;
      }
      br.close();
    } catch (IOException e) {
      System.err.println("command error");
      return null;
    }
    return result;
  }

}
