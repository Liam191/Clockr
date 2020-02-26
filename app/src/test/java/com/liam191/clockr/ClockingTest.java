package com.liam191.clockr;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClockingTest {

    // TODO: Remove duplicate test string "working"
    // TODO: Make Clocking immutable
    @Test
    public void createClockingTest(){
       Clocking workClocking = new Clocking("working");
       assertEquals(workClocking.label, "working");
    }
}
