package com.mazaiting.util;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Crash日志工具类
 * Created by mazaiting on 2017/9/12.
 */

public class CrashLogUtil {
  private static final SimpleDateFormat sTimeFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS",
      Locale.getDefault());
  /**
   * 写文件
   * @param file 文件
   * @param throwable 异常
   */
  public static void writeFile(File file, Throwable throwable) {
    try {
      file.getParentFile().mkdirs();
      if (file.exists()) {
        file.delete();
      }
      file.createNewFile();

      String time = sTimeFormat.format(Calendar.getInstance().getTime());
      synchronized (file){
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        PrintWriter printWriter = null;
        try {
          fileWriter =  new FileWriter(file, true);
          bufferedWriter = new BufferedWriter(fileWriter);
          printWriter = new PrintWriter(fileWriter);

          bufferedWriter.append(time)
              .append(" ")
              .append("E")
              .append('/')
              .append("CrashHandler")
              .append(" ")
              .append(throwable.getMessage())
              .append('\n');

          bufferedWriter.flush();
          throwable.printStackTrace(printWriter);
          printWriter.flush();
          fileWriter.flush();
        }catch (IOException e){
          closeQuietly(fileWriter);
          closeQuietly(bufferedWriter);
          closeQuietly(printWriter);
        }
      }

    } catch (Exception e){
      e.printStackTrace();
    }
  }

  /**
   * 关闭流
   * @param closeable 实现了Closeable接口的流
   */
  private static void closeQuietly(Closeable closeable) {
    try {
      closeable.close();
    } catch (IOException e) {}
  }
}
