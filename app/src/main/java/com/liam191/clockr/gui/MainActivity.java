package com.liam191.clockr.gui;

import android.os.Bundle;

import com.liam191.clockr.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(R.id.dayListFragment);
    }
}
