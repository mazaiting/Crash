package com.mazaiting.crash;

import java.io.File;

/**
 * 全局异常监听
 * Created by mazaiting on 2017/9/12.
 */
public interface CrashListener {
  /**
   * 保存异常文件
   * @param file 异常文件
   */
  void saveCrash(File file);
}
