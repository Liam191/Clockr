package com.liam191.clockr.gui.dayview;

import com.liam191.clockr.repo.DayViewModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

public class ClockrFragmentFactory extends FragmentFactory {
    private DayViewModel.Builder builder;

    public ClockrFragmentFactory(DayViewModel.Builder builder){
        this.builder = builder;
    }

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
        if(DayListFragment.class.getName().equals(className)){
            return new DayListFragment(builder);
        }
        return super.instantiate(classLoader, className);
    }
}
