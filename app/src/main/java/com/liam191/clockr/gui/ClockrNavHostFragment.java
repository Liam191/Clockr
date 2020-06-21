package com.liam191.clockr.gui;

import android.os.Bundle;

import com.liam191.clockr.AppContainer;
import com.liam191.clockr.ClockrApplication;
import com.liam191.clockr.gui.dayview.ClockrFragmentFactory;

import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

public class ClockrNavHostFragment extends NavHostFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AppContainer container = ((ClockrApplication)this.getActivity().getApplication())
                .getAppContainer();
        getChildFragmentManager().setFragmentFactory(new ClockrFragmentFactory(container.getDayViewModelBuilder()));

        super.onCreate(savedInstanceState);
    }
}
