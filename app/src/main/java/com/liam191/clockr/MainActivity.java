package com.liam191.clockr;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.liam191.clockr.repo.ClockingDayViewModel;

import org.threeten.bp.ZonedDateTime;

public class MainActivity extends AppCompatActivity {

    private ClockingDayViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppContainer container = ((ClockrApplication) getApplication()).getAppContainer();
        viewModel = new ViewModelProvider(
                this,
                container.clockingDayViewModelBuilder()
                        .ofDate(ZonedDateTime.now())
        ).get(ClockingDayViewModel.class);
    }
}
