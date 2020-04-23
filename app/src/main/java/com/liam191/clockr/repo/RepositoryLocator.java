package com.liam191.clockr.repo;

public final class RepositoryLocator {

    private volatile ClockingRepository clockingRepositoryInstance;

    // TODO: Create database instances, etc. to initialise ClockingRepository

    private static class RepositoryLocatorSingleton {
        private static final RepositoryLocator INSTANCE = new RepositoryLocator();
    }

    public static RepositoryLocator getInstance(){
        return RepositoryLocatorSingleton.INSTANCE;
    }

    public ClockingRepository getClockingRepositoryInstance(){
        ClockingRepository localRepo = clockingRepositoryInstance;
        if (localRepo == null) {
            synchronized (this) {
                localRepo = clockingRepositoryInstance;
                if (localRepo == null) {
                    clockingRepositoryInstance = localRepo = new ClockingRepository();
                }
            }
        }
        return localRepo;
    }

    public void setClockingRepositoryInstance(ClockingRepository clockingRepository) {
        this.clockingRepositoryInstance = clockingRepository;
    }
}
