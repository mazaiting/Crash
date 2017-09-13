package com.mazaiting.report;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.mazaiting.MailBuilder;
import java.io.File;
import java.util.ArrayList;

/**
 * 邮件发送错误报告
 * Created by mazaiting on 2017/9/12.
 */

public class EmailReportHandler extends AbstractCrashReportHandler {

  public EmailReportHandler(Context context) {
    super(context);
  }

  /**服务器主机名*/
  private String serverHost = "smtp.qq.com";
  /**服务器器端口*/
  private String serverPort = "465";
  /**是否验证密码*/
  private boolean isValidate = true;
  /**用户名*/
  private String userName = "1449689807@qq.com";
  /**登陆验证密码*/
  private String passWord = "";
  /**发送邮件地址*/
  private String fromAddress = "1449689807@qq.com";
  /**接收邮件地址*/
  private String toAddress = "1425941077@qq.com";

  /**设置服务器主机名, 默认值为"smtp.qq.com"*/
  public EmailReportHandler setServerHost(String serverHost) {
    this.serverHost = serverHost;
    return this;
  }

  /**服务器端口, 默认为"465"*/
  public EmailReportHandler setServerPort(String serverPort) {
    this.serverPort = serverPort;
    return this;
  }

  /**是否需要验证密码, 默认为true, 表示需要验证。*/
  public EmailReportHandler setValidate(boolean validate) {
    isValidate = validate;
    return this;
  }

  /**设置用户名, 默认为"1449689807@qq.com"*/
  public EmailReportHandler setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  /**设置验证密码, 默认为空*/
  public EmailReportHandler setPassWord(String passWord) {
    this.passWord = passWord;
    return this;
  }

  /**设置邮件发送地址, 默认为"1449689807@qq.com"*/
  public EmailReportHandler setFromAddress(String fromAddress) {
    this.fromAddress = fromAddress;
    return this;
  }

  /**设置邮件接收地址, 默认为"1425941077@qq.com"*/
  public EmailReportHandler setToAddress(String toAddress) {
    this.toAddress = toAddress;
    return this;
  }

  @Override void sendReport(final String title, final String body, final File file) {
    if (TextUtils.isEmpty(passWord)){
      throw new RuntimeException("Please set your email password!");
    }
    if (!"smtp.qq.com".equals(serverHost)){
      if ("1449689807@qq.com".equals(userName)){
        throw new RuntimeException("Server host is changed already. Please reset your username!");
      }
    }
    ArrayList<File> files = new ArrayList<File>();
    files.add(file);
    new MailBuilder()
        .setServerHost("smtp.qq.com")// 设置服务器主机
        .setServerPort("465") // 设置服务器端口
        .setValidate(true) // 设置是否需要验证
        .setUserName("1449689807@qq.com") // 设置用户名
        .setPassWord("aegstpjlwlsxicdf") // 设置验证需要的密码
        .setFromAddress("1449689807@qq.com") // 设置发送邮件的地址
        .setToAddress(toAddress) // 设置接收邮件的地址
        .setTitle(title) // 设置标题
        .setContent(body) // 设置内容
        .setFileList(files) // 设置附件
        .build(); // 发送
  }
}
