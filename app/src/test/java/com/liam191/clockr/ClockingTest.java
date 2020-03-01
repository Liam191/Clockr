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
                .description("A work clocking")
                .duration(0.5)
                .build();
        assertEquals(workClocking.duration(), 0.5, 0.001);
    }

    @Test
    public void testCreateClockingHasFromTime(){
        Clocking workClocking = new Clocking.Builder("working")
                .description("A work clocking")
                .fromTime(LocalDateTime.parse("2020-03-01T18:37:50.000Z"))
                .build();
        assertTrue(workClocking.fromTime().equals(LocalDateTime.parse("2020-03-01T18:37:50.000Z")));
    }
}
