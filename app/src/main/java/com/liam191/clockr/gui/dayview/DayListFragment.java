package com.liam191.clockr.gui.dayview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liam191.clockr.AppContainer;
import com.liam191.clockr.ClockrApplication;
import com.liam191.clockr.R;
import com.liam191.clockr.repo.DayViewModel;

import org.threeten.bp.Clock;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;

public class DayListFragment extends Fragment {

    private DayViewModel.Builder viewModelBuilder;
    private Clock appClock;
    private DayViewModel viewModel;
    private RecyclerView recyclerView;
    private ClockingAdapter adapter;

    public DayListFragment(DayViewModel.Builder viewModelBuilder){
        this.viewModelBuilder = viewModelBuilder;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContainer container = ((ClockrApplication)this.getActivity().getApplication())
                .getAppContainer();
        appClock = container.getAppClock();


        viewModel = new ViewModelProvider(
                this, viewModelBuilder.ofDate(ZonedDateTime.now(appClock))
        ).get(DayViewModel.class);

        viewModel.get().observe(this, (clockings) -> adapter.updateClockingList(clockings));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.day_fragment, container, false);
        recyclerView = view.findViewById(R.id.clocking_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ClockingAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConf = new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        NavigationUI.setupWithNavController(toolbar, navController, appBarConf);
    }
}
