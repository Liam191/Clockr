package com.liam191.clockr;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class ClockrApplication extends Application {

    private AppContainer container;

    @Override
    public void onCreate(){
        super.onCreate();
        AndroidThreeTen.init(this);
        container = new AppContainerImpl(getApplicationContext());
    }

    public AppContainer getAppContainer(){
        return container;
    }
}
