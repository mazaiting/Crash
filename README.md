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

当然你也可以使用自己的服务器来获取日志文件, 在主Application中初始化
```
public class MyApplication extends Application{

  @Override public void onCreate() {
    super.onCreate();
    new HttpReportHandler(this).setUrl("网络链接");
  }
}
```
其中可以获取到字段"title", "body", "file", "title"是标题, "body"是内容,
"file"是错误日志内容。

文章地址(http://www.jianshu.com/p/d7d3c8fbeeb2)
