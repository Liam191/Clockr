package com.liam191.clockr;

import com.liam191.clockr.repo.ClockingDayViewModel;

public interface AppContainer {
    public abstract ClockingDayViewModel.Builder clockingDayViewModelBuilder();
}
