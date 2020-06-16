package com.liam191.clockr.gui.dayview;

import android.os.Bundle;

import com.liam191.clockr.R;

import androidx.appcompat.app.AppCompatActivity;

public class DayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.day_fragment_container, getSupportFragmentManager()
                        .getFragmentFactory()
                        .instantiate(getClassLoader(), DayListFragment.class.getName())
                ).commitNow();
        setContentView(R.layout.activity_day);
    }
}
