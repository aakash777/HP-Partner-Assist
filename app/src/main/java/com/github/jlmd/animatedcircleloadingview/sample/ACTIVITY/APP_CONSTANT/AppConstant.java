package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.APP_CONSTANT;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


/**
 * Created by Aakash Gupta on 9/24/2015.
 */

public class AppConstant {

    public static String shared_partner_id = "shared_partner_id";
    public static String shared_partner_name = "shared_partner_name";
    public static String shared_wishing_time = "shared_user_wish";
    public static String shared_partner_region = "shared_partner_region";
    public static String shared_partner_state = "shared_partner_area";


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