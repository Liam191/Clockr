package com.liam191.clockr;

import com.liam191.clockr.repo.ClockingDayViewModel;

import org.threeten.bp.Clock;

public interface AppContainer {
    public abstract ClockingDayViewModel.Builder clockingDayViewModelBuilder();
    public abstract Clock getAppClock();
}
