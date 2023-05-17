package com.example.esemkalibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;

public class Utils {
    public static final String BASE_URL = "http://10.0.2.2:5000/api/";

    public static String getToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("token", "");
    }

    public static boolean isInputEmpty(EditText input) {
        if (input.getText().toString().trim().isEmpty())
            return true;
        return false;
    }
}
