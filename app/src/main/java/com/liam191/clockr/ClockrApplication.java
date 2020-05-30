package com.liam191.clockr;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class ClockrApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        AndroidThreeTen.init(this);
    }
}
