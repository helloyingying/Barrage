package com.android.liuzhuang.library.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by liuzhuang on 16/4/15.
 */
public class ContextUtil {
    private static Context applicationContext;

    public static void init(Context context) {
        applicationContext = context.getApplicationContext();
    }

    public static Context getApplicationContext() {
        if (applicationContext == null) {
            throw new NullPointerException("applicationContext can not be null. You should init first.");
        }
        return applicationContext;
    }
}
