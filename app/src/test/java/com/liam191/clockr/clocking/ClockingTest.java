package com.liam191.clockr.clocking;

import org.junit.Test;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

public class ClockingTest {

    // TODO: Unique IDs for Clockings
    // TODO: Create automatic endTime (startTime plusHours(duration)?)
    //      TODO: Ability to set endTime manually
    //      TODO: Cannot set endTime and duration at the same time??
    @Test
    public void testCreateClocking_WithLabel(){
        Clocking workClocking = new Clocking.Builder("working")
                .build();
        assertEquals(workClocking.label(), "working");
    }

    @Test
    public void testCreateClocking_WithDescription(){
        Clocking workClocking = new Clocking.Builder("working")
                .description("A work clocking")
                .build();
        assertEquals(workClocking.description(), "A work clocking");
    }

    @Test
    public void testCreateClocking_WithDurationInMinutes(){
        Clocking workClocking = new Clocking.Builder("working", 60)
                .build();
        assertEquals(workClocking.durationInMinutes(), 60);
    }

    @Test
    public void testCreateClocking_WithFromTime(){
        Clocking workClocking = new Clocking.Builder("working")
                .startTime(LocalDateTime.parse("2020-03-01T18:37:50"))
                .build();
        assertEquals(workClocking.startTime(), LocalDateTime.parse("2020-03-01T18:37:50"));
    }

    @Test
    public void testCreateClocking_HasDefaultFromTime(){
        Clock systemClockStub = new Clock() {
            @Override public ZoneId getZone() { return ZoneId.systemDefault(); }
            @Override public Clock withZone(ZoneId zone) { return systemDefaultZone(); }

            @Override
            public Instant instant() {
                return Instant.parse("2020-03-01T18:37:50.00Z");
            }
        };

        Clocking workClocking = new Clocking.Builder("working", systemClockStub)
                .build();
        assertEquals(workClocking.startTime(), LocalDateTime.parse("2020-03-01T18:37:50"));
    }

}
