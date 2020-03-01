package com.liam191.clockr;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ClockingTest {

    // TODO: Make Clocking immutable
    // TODO: Make duration field mandatory
    // TODO: Test default fromTime
    @Test
    public void testCreateClockingWithLabel(){
        Clocking workClocking = new Clocking.Builder("working")
                .build();
        assertEquals(workClocking.label(), "working");
    }

    @Test
    public void testCreateClockingWithDescription(){
        Clocking workClocking = new Clocking.Builder("working")
                .description("A work clocking")
                .build();
        assertEquals(workClocking.description(), "A work clocking");
    }

    @Test
    public void testCreateClockingWithDuration(){
        Clocking workClocking = new Clocking.Builder("working")
                .durationInMinutes(30)
                .build();
        assertEquals(workClocking.durationInMinutes(), 30);
    }

    @Test
    public void testCreateClockingWithFromTime(){
        Clocking workClocking = new Clocking.Builder("working")
                .fromTime(LocalDateTime.parse("2020-03-01T18:37:50"))
                .build();
        assertEquals(workClocking.fromTime(), LocalDateTime.parse("2020-03-01T18:37:50"));
    }
}
