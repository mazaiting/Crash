package com.mazaiting.crash2;

import android.app.Application;
import com.mazaiting.report.EmailReportHandler;

/**
 * Created by mazaiting on 2017/9/12.
 */

public class MyApplication extends Application{

  @Override public void onCreate() {
    super.onCreate();
    //new HttpReportHandler(this);
    new EmailReportHandler(this)
      .setServerHost("smtp.qq.com")
      .setServerPort("465")
      .setValidate(true)
      .setUserName("1449689807@qq.com")
      .setPassWord("aegstpjlwlsxicdf")
      .setFromAddress("1449689807@qq.com")
      .setToAddress("1425941077@qq.com");
  }
}