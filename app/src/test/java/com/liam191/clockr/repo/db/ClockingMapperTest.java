package com.liam191.clockr.repo.db;

import com.liam191.clockr.clocking.Clocking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClockingMapperTest {
    // TODO: Handling errors if conversion fails
    // TODO: Complete testing here
    @Test
    public void testConverter_fromClockingToEntity(){
        Clocking clocking = new Clocking.Builder("TestClocking", 45)
                .build();

        ClockingEntity entity = ClockingMapper.map(clocking);

        assertEquals(entity.label, clocking.label());
        assertEquals(entity.description, clocking.description());
        assertEquals(entity.durationInMinutes, clocking.durationInMinutes());
        assertEquals(entity.startTime, clocking.startTime().getTime());
    }

    @Test
    public void testConverter_fromEntityToClocking(){
        ClockingEntity entity = new ClockingEntity();

    }
}
