package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.APP_CONSTANT;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


/**
 * Created by Aakash Gupta on 9/24/2015.
 */

public class AppConstant {

    public static void savePreferences(Activity activity, String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String readPreferences(Activity activity, String key, String defaultValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        return sp.getString(key, defaultValue);
    }

}