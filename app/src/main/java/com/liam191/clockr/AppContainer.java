package com.liam191.clockr;

import com.liam191.clockr.repo.DayViewModel;

import org.threeten.bp.Clock;

public interface AppContainer {
    public abstract DayViewModel.Builder getDayViewModelBuilder();
    public abstract Clock getAppClock();
}
