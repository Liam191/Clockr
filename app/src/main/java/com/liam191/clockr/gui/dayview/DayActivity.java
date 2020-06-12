package com.liam191.clockr.gui.dayview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liam191.clockr.AppContainer;
import com.liam191.clockr.ClockrApplication;
import com.liam191.clockr.R;
import com.liam191.clockr.repo.DayViewModel;

import org.threeten.bp.Clock;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DayActivity extends AppCompatActivity {

    private Clock appClock;
    private DayViewModel viewModel;
    private RecyclerView recyclerView;
    private ClockingAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        AppContainer container = ((ClockrApplication) getApplication()).getAppContainer();
        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "### appContainer > "+ container);

        appClock = container.getAppClock();
        Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "### appClock > " +ZonedDateTime.ofInstant(appClock.instant(), appClock.getZone()).toString());

        recyclerView = findViewById(R.id.clocking_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClockingAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(
                this, container.clockingDayViewModelBuilder().ofDate(ZonedDateTime.now(appClock))
        ).get(DayViewModel.class);

        viewModel.get().observe(this, (clockings) -> adapter.updateClockingList(clockings));
    }

}
