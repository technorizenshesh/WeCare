package com.tech.docarelat.App;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPref {

    /* renamed from: sp */
    public static SharedPreferences f154sp;

    public static void saveData(Context context, String str, String str2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ryde", 0);
        f154sp = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static String getData(Context context, String str, String str2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ryde", 0);
        f154sp = sharedPreferences;
        return sharedPreferences.getString(str, str2);
    }

    public static void DeleteData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ryde", 0);
        f154sp = sharedPreferences;
        sharedPreferences.edit().clear().commit();
    }

    public static void NullData(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ryde", 0);
        f154sp = sharedPreferences;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(str, (String) null);
        edit.commit();
    }
}
