package com.example.laion.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.example.laion.myapplication.PreferencesUtility.LOGGED_ID;
import static com.example.laion.myapplication.PreferencesUtility.LOGGED_IN_PREF;

public class SaveSharedPreference {

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    public static void setId(Context context, int Id) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt(LOGGED_ID, Id);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

    public static int getId(Context context) {
        return getPreferences(context).getInt(LOGGED_ID,1);
    }
}