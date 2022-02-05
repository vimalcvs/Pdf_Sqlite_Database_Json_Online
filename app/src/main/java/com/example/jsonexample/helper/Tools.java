package com.example.jsonexample.helper;


import android.app.Activity;

import com.example.jsonexample.R;

public class Tools {

    SharedPref sharedPref;
    public Tools(Activity activity) {
        this.sharedPref = new SharedPref(activity);
    }

    public static void getTheme(Activity activity) {
        SharedPref sharedPref = new SharedPref(activity);
        if (sharedPref.getIsDarkTheme()) {
            activity.setTheme(R.style.AppDarkTheme);
        } else {
            activity.setTheme(R.style.AppTheme);
        }
    }
}
