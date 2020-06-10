package com.liam191.clockr;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.ClockingDayViewModel;

import org.threeten.bp.Clock;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private Clock appClock;
    private ClockingDayViewModel viewModel;
    private RecyclerView recyclerView;
    private ClockingAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        ).get(ClockingDayViewModel.class);

        viewModel.get().observe(this, (clockings) -> adapter.updateClockingList(clockings));
    }

    // TODO: Order clockings
    //       - User sets chronological or reverse-chronological ordering
    private class ClockingAdapter extends RecyclerView.Adapter<ClockingAdapter.ClockingViewHolder> {
        private List<Clocking> clockingList;

        public ClockingAdapter(List<Clocking> clockingList) {
            this.clockingList = clockingList;
        }

        public void updateClockingList(List<Clocking> clockingList){
            this.clockingList = clockingList;
            notifyDataSetChanged();
        }

        private class ClockingViewHolder extends RecyclerView.ViewHolder {
            public TextView clockingLabel;
            public TextView clockingDescription;
            public TextView clockingStartDate;
            public ImageView icon;

            public ClockingViewHolder(View itemView) {
                super(itemView);
                this.clockingLabel = itemView.findViewById(R.id.label);
                this.clockingDescription = itemView.findViewById(R.id.description);
                this.clockingStartDate = itemView.findViewById(R.id.start_time);
                this.icon = itemView.findViewById(R.id.icon);
            }
        }

        @NonNull
        @Override
        public ClockingAdapter.ClockingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ClockingAdapter.ClockingViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.clocking_listitem, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull ClockingViewHolder holder, int position) {
            Clocking clocking = clockingList.get(position);
            holder.clockingLabel.setText(clocking.label());
            holder.clockingStartDate.setText(clocking.startTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));

            if(clocking.description().equals("")){
                // Hide this view if there's nothing to display.
                // We set height to 0 over visibility.GONE so the constraints of the layout still work
                holder.clockingDescription.setHeight(0);
            } else {
                holder.clockingDescription.setText(clocking.description());
            }

            Random rnd = new Random();
            holder.icon.setColorFilter(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));


        }

        @Override
        public int getItemCount() {
            return clockingList.size();
        }
    };

}
