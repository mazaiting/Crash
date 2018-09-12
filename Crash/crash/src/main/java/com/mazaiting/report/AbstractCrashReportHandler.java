package com.mazaiting.report;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import com.mazaiting.crash.CrashHandler;
import com.mazaiting.crash.CrashListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 异常获取抽象类
 * Created by mazaiting on 2017/9/12.
 */

public abstract class AbstractCrashReportHandler {
  
  public AbstractCrashReportHandler(Context context) {
    handlerInit(context);
  }
  
  /**
   * 初始化CrashHandler
   * @param context 设备上下文
   */
  private void handlerInit(final Context context) {
    CrashHandler handler = CrashHandler.getInstance();
    // 异常监听
    CrashListener crashListener = new CrashListener() {
      @Override
      public void saveCrash(File file) {
        sendReport(buildTitle(context), buildBody(context), file);
      }
    };
    handler.init(new File(context.getExternalCacheDir(),
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                    .format(System.currentTimeMillis()) + ".log"), crashListener);
    Thread.setDefaultUncaughtExceptionHandler(handler);
  }
  
  /**
   * 发送报告
   * @param title 标题
   * @param body 内容
   * @param file 文件
   */
  protected abstract void sendReport(String title, String body, File file);

  /**
   * 构建标题
   * @param context 上下文
   * @return 标题
   */
  private String buildTitle(Context context) {
    return "Crash Log: "
        + context.getPackageManager().getApplicationLabel(context.getApplicationInfo());
  }

  /**
   * 构建内容
   * @param context 上下文
   * @return 内容
   */
  private String buildBody(Context context) {
    StringBuilder sb = new StringBuilder();
    // 应用信息
    sb.append("APPLICATION INFORMATION").append("<br/>");
    PackageManager pm = context.getPackageManager();
    ApplicationInfo ai = context.getApplicationInfo();
    // 应用
    sb.append("Application : ").append(pm.getApplicationLabel(ai)).append("<br/>");

    try {
      PackageInfo pi = pm.getPackageInfo(ai.packageName, 0);
      // 版本号
      sb.append("Version Code: ").append(pi.versionCode).append("<br/>");
      // 版本名
      sb.append("Version Name: ").append(pi.versionName).append("<br/>");
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

    // 设备信息
    sb.append("<br/>").append("DEVICE INFORMATION").append("<br/>");
    sb.append("Board: ").append(Build.BOARD).append("<br/>");
    sb.append("BOOTLOADER: ").append(Build.BOOTLOADER).append("<br/>");
    sb.append("BRAND: ").append(Build.BRAND).append("<br/>");
    sb.append("CPU_ABI: ").append(Build.CPU_ABI).append("<br/>");
    sb.append("CPU_ABI2: ").append(Build.CPU_ABI2).append("<br/>");
    sb.append("DEVICE: ").append(Build.DEVICE).append("<br/>");
    sb.append("DISPLAY: ").append(Build.DISPLAY).append("<br/>");
    sb.append("FINGERPRINT: ").append(Build.FINGERPRINT).append("<br/>");
    sb.append("HARDWARE: ").append(Build.HARDWARE).append("<br/>");
    sb.append("HOST: ").append(Build.HOST).append("<br/>");
    sb.append("ID: ").append(Build.ID).append("<br/>");
    sb.append("MANUFACTURER: ").append(Build.MANUFACTURER).append("<br/>");
    sb.append("PRODUCT: ").append(Build.PRODUCT).append("<br/>");
    sb.append("TAGS: ").append(Build.TAGS).append("<br/>");
    sb.append("TYPE: ").append(Build.TYPE).append("<br/>");
    sb.append("USER: ").append(Build.USER).append("<br/>");

    return sb.toString();
  }
}
