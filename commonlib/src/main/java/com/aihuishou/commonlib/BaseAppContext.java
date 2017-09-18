package com.aihuishou.commonlib;

import android.content.Context;
import com.aihuishou.httplib.HttpBaseContext;


/**
 * 类名称：BaseAppContext
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/8/14
 * 描述：TODO
 */
public class BaseAppContext {
  public static Context mAppContext;


  public static void init(Context context) {
    if (mAppContext == null) {
      mAppContext = context.getApplicationContext();
      HttpBaseContext.init(mAppContext);
    } else {
      throw new IllegalStateException("set context duplicate");
    }
  }

  public static Context getContext() {
    if (mAppContext == null) {
      throw new IllegalStateException("forget init?");
    } else {
      return mAppContext;
    }
  }
}
