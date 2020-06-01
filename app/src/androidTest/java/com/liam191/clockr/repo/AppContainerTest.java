package com.liam191.clockr.repo;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;

import com.liam191.clockr.AppContainer;

import org.junit.Test;
import org.threeten.bp.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SmallTest
public class AppContainerTest {

    @Test
    public void testClockingDayView(){
        ZonedDateTime testDate = ZonedDateTime.parse("2017-04-16T12:00:00Z[Europe/London]");

        AppContainer container = new AppContainer(ApplicationProvider.getApplicationContext());
        ClockingDayViewModel viewModel = container.clockingDayViewModelBuilder()
                .ofDate(testDate)
                .build();
        assertTrue(viewModel != null);
        assertTrue(viewModel instanceof ClockingDayViewModel);
    }
}
