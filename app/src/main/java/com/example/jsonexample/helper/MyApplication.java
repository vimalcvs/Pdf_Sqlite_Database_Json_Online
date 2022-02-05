package com.example.jsonexample.helper;

import android.app.Application;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    SharedPref sharedPref;
    public MyApplication() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sharedPref = new SharedPref(this);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}