package com.mazaiting.report;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 本地打印
 * Created by mazaiting on 2017/9/22.
 */
public class LocalReportHandler extends AbstractCrashReportHandler {

  public LocalReportHandler(Context context) {
    super(context);
  }

  @Override protected void sendReport(String title, String body, File file) {
    if (!TextUtils.isEmpty(title)){
      Log.d("Crash title: ", title);
    }
    if (!TextUtils.isEmpty(body)){
      Log.d("Crash body: ", body);
    }
    if (null != file){
      FileInputStream fis = null;
      OutputStream os = null;
      try {
        fis =  new FileInputStream(file);
        os = new ByteArrayOutputStream();
        int len;
        byte[] bytes = new byte[1024];
        while ((len = fis.read(bytes)) != -1){
          os.write(bytes, 0, len);
        }
        Log.d("Crash message: ", os.toString());
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        closeStream(fis);
        closeStream(os);
      }
    }
  }

  private void closeStream(Closeable closeable) {
    try {
      closeable.close();
    } catch (IOException e) {}
  }
}
