package com.mazaiting.crash;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.mazaiting.util.CrashLogUtil;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 处理异常信息Handler
 * Created by mazaiting on 2017/9/12.
 */
public class CrashHandler implements UncaughtExceptionHandler {
  // 创建当前对象
  private static CrashHandler mHandler = new CrashHandler();
  // 获取当前默认的异常处理器
  // private static final UncaughtExceptionHandler sDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
  // 单线程池
  private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();
  // Crash监听
  private CrashListener mCrashListener = null;
  // 异常文件
  private File mExceptionFile = null;

  @Override public void uncaughtException(Thread thread, Throwable throwable) {
    Log.e("TAG", throwable.getMessage());
    CrashLogUtil.writeFile(mExceptionFile, throwable);
    // 线程池执行任务
    THREAD_POOL.execute(new Runnable() {
      @Override public void run() {
        if (null != mCrashListener) {
          mCrashListener.saveCrash(mExceptionFile);
        }
      }
    });
    //sDefaultHandler.uncaughtException(thread, throwable);
  }

  /**
   * 获取实例
   * @return CrashHandler实例
   */
  public static CrashHandler getInstance() {
    return mHandler;
  }

  /**
   * 初始化文件与监听器
   * @param file 文件
   * @param listener 监听器
   */
  public void init(File file, CrashListener listener) {
    this.mExceptionFile = file;
    this.mCrashListener = listener;
    // 处理点击后无反应的状态
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override public void run() {

        while (true) {
          try {
            Looper.loop();
          } catch (Throwable e) {
            if (mHandler != null) {
              mHandler.uncaughtException(Looper.getMainLooper().getThread(), e);
            }
          }
        }
      }
    });
  }
}
