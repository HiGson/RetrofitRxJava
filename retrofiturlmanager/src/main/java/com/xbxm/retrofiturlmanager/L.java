package com.xbxm.retrofiturlmanager;


import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * Log统一管理类
 */
public class L {


    private static boolean sIsLoggable = BuildConfig.DEBUG;
    private static String sTag = BuildConfig.APPLICATION_ID;

    private L() {
        /* cannot be instantiated */

    }

    static {
        tag(BuildConfig.APPLICATION_ID);
        addAndroidLogAdapter();
    }


    private static void addAndroidLogAdapter() {


        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return sIsLoggable;
            }
        });
    }

    public static void tag(String tag) {
        sTag = tag;
        Logger.t(sTag);
    }

    public static void setLoggablee(boolean isLoggable) {
        sIsLoggable = isLoggable;
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        i(sTag, msg);
    }

    public static void d(String msg) {
        d(sTag, msg);
    }

    public static void e(String msg) {
        e(sTag, msg);
    }

    public static void v(String msg) {
        v(sTag, msg);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        outputLog(Logger.INFO, tag, msg);
    }

    public static void d(String tag, String msg) {
        outputLog(Logger.DEBUG, tag, msg);
    }

    public static void e(String tag, String msg) {
        outputLog(Logger.ERROR, tag, msg);
    }

    public static void v(String tag, String msg) {
        outputLog(Logger.VERBOSE, tag, msg);
    }

    private static void outputLog(int level, String tag, String msg) {
        Logger.log(level, tag, msg, null);
    }
}
