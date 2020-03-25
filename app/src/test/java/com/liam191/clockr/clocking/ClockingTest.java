package com.liam191.clockr.clocking;

import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SimplifiableJUnitAssertion")
public class ClockingTest {

    // TODO: Create better validation around Strings, ints, date ranges, etc.
    // TODO: Create automatic endTime (startTime plusHours(duration)?)
    //      TODO: Ability to set endTime manually
    //      TODO: Cannot set endTime and duration at the same time??
    // TODO: Fix startTime/Date mutability
    @Test
    public void testCreateClocking_WithLabel(){
        Clocking workClocking = new Clocking.Builder("working", 10)
                .build();
        assertEquals(workClocking.label(), "working");
    }

    @Test
    public void testCreateClocking_WithLabelAndTrimmedWhitespace(){
        Clocking workClocking = new Clocking.Builder("      working      ", 10)
                .build();
        assertEquals(workClocking.label(), "working");
    }

    @Test
    public void testCreateClocking_ThrowsExceptionWithNullLabel(){
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new Clocking.Builder(null, 10).build()
        );
        assertTrue(e.getMessage().contains("label cannot be null"));
    }

    @Test
    public void testCreateClocking_WithDescription(){
        Clocking workClocking = new Clocking.Builder("working", 20)
                .description("A work clocking")
                .build();
        assertEquals(workClocking.description(), "A work clocking");
    }

    @Test
    public void testCreateClocking_WithDescriptionAndTrimmedWhitespace(){
        Clocking workClocking = new Clocking.Builder("working", 20)
                .description("         A work clocking          ")
                .build();
        assertEquals(workClocking.description(), "A work clocking");
    }

    @Test
    public void testCreateClocking_ThrowsExceptionWithNullDescription(){
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new Clocking.Builder("working", 10)
                        .description(null)
                        .build()
        );
        assertTrue(e.getMessage().contains("description cannot be null"));
    }


    @Test
    public void testCreateClocking_WithDurationInMinutes(){
        Clocking workClocking = new Clocking.Builder("working", 60)
                .build();
        assertEquals(workClocking.durationInMinutes(), 60);
    }

    @Test
    public void testCreateClocking_ThrowsExceptionWithNegativeDurationInMinutes(){
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new Clocking.Builder("working", -720).build()
        );
        assertTrue(e.getMessage().contains("durationInMinutes cannot be zero or negative"));
    }

    @Test
    public void testCreateClocking_WithStartTime(){
        Clocking workClocking = new Clocking.Builder("working", 70)
                .startTime(new Date(2020, 03, 01, 18 , 37, 50))
                .build();
        assertEquals(workClocking.startTime(), new Date(2020, 03, 01, 18 , 37, 50));
    }

    @Test
    public void testCreateClocking_HasDefaultStartTime(){
        Clocking workClocking = new Clocking.Builder("working", 60)
                .build();
        assertEquals(workClocking.startTime().getTime(),    new Date().getTime(), 100);
    }

    @Test
    public void testCreateClocking_ThrowsExceptionWithNullStartTime(){
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new Clocking.Builder("working", 60)
                        .startTime(null)
                        .build()
        );
        assertTrue(e.getMessage().contains("startTime cannot be null"));
    }


    @Test
    public void testClockingEquals(){
        Clocking clockingX = new Clocking.Builder("Same clocking", 60).build();
        Clocking clockingY = new Clocking.Builder("Same clocking", 60).build();
        Clocking clockingZ = new Clocking.Builder("Same clocking", 60).build();
        Clocking otherClocking = new Clocking.Builder("Different clocking", 120).build();

        assertTrue(clockingX.equals(clockingX));

        assertTrue(clockingX.equals(clockingY));
        assertTrue(clockingY.equals(clockingX));

        assertTrue(clockingX.equals(clockingY));
        assertTrue(clockingY.equals(clockingZ));
        assertTrue(clockingZ.equals(clockingX));

        assertFalse(clockingX.equals(otherClocking));
        assertFalse(otherClocking.equals(clockingX));
        assertFalse(clockingX.equals(null));
    }

    @Test
    public void testClockingEquals_fromSameAndDifferentBuilders(){
        Clocking.Builder builderOne = new Clocking.Builder("Clocking one", 60);
        Clocking.Builder builderTwo = new Clocking.Builder("Clocking one", 60);

        Clocking clockingA = builderOne.build();
        Clocking clockingB = builderOne.build();
        Clocking clockingX = builderTwo.build();
        Clocking clockingY = builderTwo.build();

        assertTrue(clockingA.equals(clockingB));
        assertTrue(clockingB.equals(clockingX));
        assertTrue(clockingX.equals(clockingY));
        assertTrue(clockingY.equals(clockingA));
    }
}
