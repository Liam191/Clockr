package com.liam191.clockr;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClockingTest {

    // TODO: Remove duplicate test string "working"
    // TODO: Make Clocking immutable
    @Test
    public void createClockingTest(){
        String clockingLabel = "working";
        Clocking workClocking = new Clocking(clockingLabel);
        assertEquals(workClocking.label, clockingLabel);
    }
}
