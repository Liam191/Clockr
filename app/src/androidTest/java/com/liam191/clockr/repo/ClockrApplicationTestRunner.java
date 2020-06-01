package com.liam191.clockr.repo;

import android.app.Application;
import android.content.Context;

import androidx.test.runner.AndroidJUnitRunner;

import com.liam191.clockr.ClockrApplication;

public class ClockrApplicationTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, FakeClockrApplication.class.getName(), context);
    }

    public static class FakeClockrApplication extends ClockrApplication {
        @Override
        public void onCreate(){
            super.onCreate();
        }
    }
}
