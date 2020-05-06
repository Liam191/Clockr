package com.liam191.clockr.repo;

public final class ClockingRepositorySingleton {

    // TODO: Create database instances, etc. to initialise ClockingRepository
    private static class Singleton {
        private static final ClockingDayView INSTANCE = new ClockingDayView();
    }

    public static ClockingDayView getInstance(){
        return Singleton.INSTANCE;
    }
}
