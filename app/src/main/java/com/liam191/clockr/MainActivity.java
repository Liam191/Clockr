package com.liam191.clockr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.ClockingDayViewModel;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private ClockingDayViewModel viewModel;
    private RecyclerView recyclerView;
    private ClockingAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.clocking_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClockingAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        AppContainer container = ((ClockrApplication) getApplication()).getAppContainer();
        viewModel = new ViewModelProvider(
                this, container.clockingDayViewModelBuilder().ofDate(ZonedDateTime.now())
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
        }

        private class ClockingViewHolder extends RecyclerView.ViewHolder {
            public TextView clockingLabel;
            public EditText clockingStartDate;

            public ClockingViewHolder(View itemView) {
                super(itemView);
                this.clockingLabel = itemView.findViewById(R.id.clocking_label);
                this.clockingStartDate = itemView.findViewById(R.id.clocking_startdate);
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
            holder.clockingStartDate.setText(clocking.startTime().toString());
        }

        @Override
        public int getItemCount() {
            return clockingList.size();
        }
    };

}
