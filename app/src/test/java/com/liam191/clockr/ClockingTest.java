package com.liam191.clockr;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClockingTest {

    // TODO: Make Clocking immutable
    // TODO: Implement Clocking Builder
    @Test
    public void createClocking_WithLabel(){
        Clocking workClocking = new Clocking.Builder("working")
                .build();
        assertEquals(workClocking.label(), "working");
    }

    @Test
    public void createClocking_WithDescription(){
        Clocking workClocking = new Clocking.Builder("working")
                .description("A work clocking")
                .build();
        assertEquals(workClocking.description(), "A work clocking");
    }
}
