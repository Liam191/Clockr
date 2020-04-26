package com.liam191.clockr.repo;

public final class ClockingRepositorySingleton {

    // TODO: Create database instances, etc. to initialise ClockingRepository
    private static class Singleton {
        private static final ClockingRepository INSTANCE = new ClockingRepository();
    }

    public static ClockingRepository getInstance(){
        return Singleton.INSTANCE;
    }
}
