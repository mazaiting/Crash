# Crash
Android 全局异常捕获，点击按钮抛出异常后仍与未点击时界面状态一致

使用，在项目的主Application中初始化
```
public class MyApplication extends Application{

  @Override public void onCreate() {
    super.onCreate();
    new EmailReportHandler(this)
      .setServerHost("smtp.qq.com") // 服务器地址
      .setServerPort("465") // 服务器端口
      .setValidate(true) // 需要密码验证
      .setUserName("1449689807@qq.com") // 邮箱的登录名
      .setPassWord("****************") // 邮箱验证的密码
      .setFromAddress("1449689807@qq.com") // 发送邮件地址
      .setToAddress("1425941077@qq.com"); // 接收邮件地址
  }
}
```

如果你和我一样同样使用的是QQ邮箱, 则可精简为
```
public class MyApplication extends Application{

  @Override public void onCreate() {
    super.onCreate();
    new EmailReportHandler(this)
      .setUserName("1449689807@qq.com") // 邮箱的登录名
      .setPassWord("****************") // 邮箱验证的密码
      .setFromAddress("1449689807@qq.com") // 发送邮件地址
      .setToAddress("1425941077@qq.com"); // 接收邮件地址
  }
}
```

当然你也可以将日志文件存储在本地, 在主Application中初始化
```
public class MyApplication extends Application{

  @Override public void onCreate() {
    super.onCreate();
    // 文件默认路径: /data/data/应用包名/files/crash.log
    new LocalReportHandler(this);
  }
}
```

如果这两个类无法满足你的需求，你也可以自定义一个MyHandler, 并继承AbstractCrashReportHandler,
实现它的构造方法与sendReport(String title, String body, File file)方法。其中title是标题,
body是内容, file是错误日志文件。
```
public class MyHandler extends AbstractCrashReportHandler {
  MyHandler(Context context) {
    super(context);
  }

  @Override protected void sendReport(String title, String body, File file) {

  }
}

// 并在Application中初始化
public class MyApplication extends Application{

  @Override public void onCreate() {
    super.onCreate();
    new MyHandler(this);
  }
}
```

文章地址(http://www.jianshu.com/p/d7d3c8fbeeb2)
