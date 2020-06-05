package com.liam191.clockr;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liam191.clockr.clocking.Clocking;
import com.liam191.clockr.repo.ClockingDayViewModel;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ClockingDayViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.clocking_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerView.Adapter() {
            private List<Clocking> clockingList = new ArrayList<>();

            class ViewHolder extends RecyclerView.ViewHolder{
                public TextView clockingLabel;
                public EditText clockingStartDate;
                public EditText clockingStartTime;

                public ViewHolder(View clockingLabel){
                    super(clockingLabel);
                    this.clockingLabel = (TextView)clockingLabel;
                }

                public ViewHolder(@NonNull TextView clockingLabel, EditText clockingStartDate, EditText clockingStartTime) {
                    this(clockingLabel);
                    this.clockingStartDate = clockingStartDate;
                    this.clockingStartTime = clockingStartTime;
                }
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        };
        recyclerView.setAdapter(adapter);


        AppContainer container = ((ClockrApplication) getApplication()).getAppContainer();
        viewModel = new ViewModelProvider(
                this, container.clockingDayViewModelBuilder().ofDate(ZonedDateTime.now())
        ).get(ClockingDayViewModel.class);

        viewModel.get().observe(this, (clockings)->{
            //((TextView) findViewById(R.id.test_text)).setText(clockings.toString());
        });

    }
}
