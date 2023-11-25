package com.mirfatif.mylocation;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.mirfatif.mylocation.util.Utils;
import java.io.PrintWriter;
import java.io.StringWriter;

public class App extends Application {

  private static final String TAG = "App";

  private static Context mAppContext;
  private Thread.UncaughtExceptionHandler defaultExceptionHandler;

  public void onCreate() {
    super.onCreate();
    mAppContext = getApplicationContext();
    updateContext();
    defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

    Thread.setDefaultUncaughtExceptionHandler(
        (t, e) -> {
          Log.e(TAG, e.toString());

          StringWriter stringWriter = new StringWriter();
          PrintWriter writer = new PrintWriter(stringWriter, true);
          e.printStackTrace(writer);
          writer.close();
          Utils.writeCrashLog(stringWriter.toString());

          defaultExceptionHandler.uncaughtException(t, e);
        });

    Utils.runBg(this::getEncPrefs);
  }

  public static void updateContext() {
    mAppContext = Utils.setLocale(mAppContext);
  }

  public static Context getCxt() {
    return mAppContext;
  }

  public static Resources getRes() {
    return mAppContext.getResources();
  }

  private void getEncPrefs() {
    Utils.getEncPrefs();
  }
}
