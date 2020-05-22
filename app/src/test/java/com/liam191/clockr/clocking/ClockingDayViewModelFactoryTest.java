package com.liam191.clockr.clocking;

import com.liam191.clockr.repo.ClockingDayViewModelFactory;

import org.junit.Test;
import org.threeten.bp.ZonedDateTime;

public class ClockingDayViewModelFactoryTest {

    // TODO: Use a Builder for creating ClockingDayViewModels to simplify the API
    //          - A builder can add common default dependencies without having them injected or
    //            forcing the consumer to build them. In the case of ViewModels, we'd be getting
    //            Activities or a custom ViewModelProvider class to create and manage the Repository
    //            and DAO instances. With ViewModelProvider it might be worth looking into this, but
    //            it does seem like we'd end up with a ServiceLocator type pattern
    @Test
    public void testFactory(){
        ZonedDateTime testDate = ZonedDateTime.parse("2020-01-03T00:00:00Z[Europe/London]");
        new ClockingDayViewModelFactory(testDate);
    }
}
