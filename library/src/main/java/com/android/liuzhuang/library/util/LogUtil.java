package com.android.liuzhuang.library.util;

import android.text.TextUtils;
import android.util.Log;

public class LogUtil {

    public static boolean isShow(){
        return true;
    }

    public static boolean isShow(Object msg){
        return isShow() && msg != null && !TextUtils.isEmpty(msg.toString());
    }

    public static void d(String tag, Object msg) {
        if (isShow(msg)) {
            Log.d(tag,msg.toString());
        }
    }

    public static void i(String tag, Object msg) {
        if (isShow(msg)) {
            Log.i(tag, msg.toString());
        }
    }

    public static void w(String tag, Object msg) {
        if (isShow(msg)) {
            Log.w(tag, msg.toString());
        }
    }

    public static void e(String tag, Object msg) {
        if (isShow(msg)) {
            Log.e(tag, msg.toString());
        }
    }
}
