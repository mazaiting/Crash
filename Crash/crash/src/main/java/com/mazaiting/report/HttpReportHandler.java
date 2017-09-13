package com.mazaiting.report;

import android.content.Context;
import android.text.TextUtils;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.MultipartFormDataBody;
import java.io.File;

/**
 * Http发送文件
 * Created by mazaiting on 2017/9/12.
 * User the Handler
 *
 * public class MyApplication extends Application{
      public void onCreate() {
        super.onCreate();
        new HttpReportHandler(this);
      }
  }
 *
 */
public class HttpReportHandler extends AbstractCrashReportHandler {
  /**服务器地址链接*/
  private String url;
  public HttpReportHandler(Context context) {
    super(context);
  }

  public HttpReportHandler setUrl(String url) {
    this.url = url;
    return this;
  }
  @Override void sendReport(String title, String body, File file) {
    if (TextUtils.isEmpty(url)){
      throw new RuntimeException("Please set your net url!");
    }
    AsyncHttpPost post = new AsyncHttpPost(url);
    MultipartFormDataBody multipartFormDataBody = new MultipartFormDataBody();
    multipartFormDataBody.addStringPart("title", title);
    multipartFormDataBody.addStringPart("body",body);
    multipartFormDataBody.addFilePart("file", file);
    post.setBody(multipartFormDataBody);
    AsyncHttpClient.getDefaultInstance().executeString(post, new AsyncHttpClient.StringCallback(){
      @Override
      public void onCompleted(Exception ex, AsyncHttpResponse source, String result) {
        if (ex != null) {
          ex.printStackTrace();
        }
      }
    });
  }
}
