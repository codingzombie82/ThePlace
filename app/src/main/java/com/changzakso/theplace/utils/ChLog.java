package com.changzakso.theplace.utils;

import android.util.Log;

import com.changzakso.theplace.BuildConfig;

public class ChLog {
    private static boolean enabled = BuildConfig.DEBUG;

    public static void d(String tag, String text) {
        if (!enabled) return;

        Log.d(tag, text);
    }

    public static void d(String text) {
        if (!enabled) return;

        Log.d("tag", text);
    }

    public static void d(String tag, Class<?> cls, String text) {
        if (!enabled) return;

        Log.d(tag, cls.getName() + "." + text);
    }


    public static void e(String tag, String text) {
        if (!enabled) return;

        Log.e(tag, text);
    }
}
