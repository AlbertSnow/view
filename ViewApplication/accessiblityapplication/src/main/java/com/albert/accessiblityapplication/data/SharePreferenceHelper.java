package com.albert.accessiblityapplication.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/5/15.
 */

public class SharePreferenceHelper {
    private static final String ACCESSIBILITY = "ACCESSIBILITY";
    private static final String IS_CONTACT_BTN_CLICKED = "IS_CONTACT_BTN_CLICKED";
    private static final String LAST_ACCESS_UPDATE_TIME = "LAST_ACCESS_UPDATE_TIME";

    public static void isContactClicked(Context context) {
        SharedPreferences preference = context.getSharedPreferences(ACCESSIBILITY, Context.MODE_APPEND);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(IS_CONTACT_BTN_CLICKED, true);
        editor.putLong(LAST_ACCESS_UPDATE_TIME, System.currentTimeMillis());
        editor.apply();
    }
}
