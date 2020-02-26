package com.liam191.clockr;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClockingTest {

    // TODO: Make Clocking immutable
    // TODO: Implement Clocking Builder
    // TODO: Better encapsulate state
    @Test
    public void createClocking_WithLabel(){
        Clocking workClocking = new Clocking.Builder("working")
                .build();
        assertEquals(workClocking.label(), "working");
    }
}
