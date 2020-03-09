package com.liam191.clockr.clocking;

import org.junit.Test;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;

@SuppressWarnings("SimplifiableJUnitAssertion")
public class ClockingTest {

    private class SystemClockStub {
        private final LocalDateTime stubbedTime;

        SystemClockStub(final String timeString) {
            stubbedTime = LocalDateTime.parse(timeString);
        }

        Clock getClock() {
            return new Clock() {
                @Override public ZoneId getZone() { return ZoneId.systemDefault(); }
                @Override public Clock withZone(ZoneId zone) { return systemDefaultZone(); }

                @Override
                public Instant instant() {
                    return stubbedTime.atZone(ZoneId.systemDefault()).toInstant();
                }
            };
        }
    }

    // TODO: Create better validation around Strings, ints, date ranges, etc.
    // TODO: Create automatic endTime (startTime plusHours(duration)?)
    //      TODO: Ability to set endTime manually
    //      TODO: Cannot set endTime and duration at the same time??
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
    public void testCreateClocking_WithDurationInMinutes(){
        Clocking workClocking = new Clocking.Builder("working", 60)
                .build();
        assertEquals(workClocking.durationInMinutes(), 60);
    }

    @Test
    public void testCreateClocking_WithFromTime(){
        Clocking workClocking = new Clocking.Builder("working", 70)
                .startTime(LocalDateTime.parse("2020-03-01T18:37:50"))
                .build();
        assertEquals(workClocking.startTime(), LocalDateTime.parse("2020-03-01T18:37:50"));
    }

    @Test
    public void testCreateClocking_HasDefaultFromTime(){
        Clock systemClockStub = new SystemClockStub("2020-03-01T18:37:50").getClock();

        Clocking workClocking = new Clocking.Builder("working", 60, systemClockStub)
                .build();
        assertEquals(workClocking.startTime(), LocalDateTime.parse("2020-03-01T18:37:50"));
    }

    @Test
    public void testClockingEquals(){
        Clock systemClockStub = new SystemClockStub("2020-03-01T18:37:50").getClock();

        Clocking clockingX = new Clocking.Builder("Same clocking", 60, systemClockStub).build();
        Clocking clockingY = new Clocking.Builder("Same clocking", 60, systemClockStub).build();
        Clocking clockingZ = new Clocking.Builder("Same clocking", 60, systemClockStub).build();
        Clocking otherClocking = new Clocking.Builder("Different clocking", 120, systemClockStub).build();

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
        Clock systemClockStub = new SystemClockStub("2020-03-01T18:37:50").getClock();

        Clocking.Builder builderOne = new Clocking.Builder("Clocking one", 60, systemClockStub);
        Clocking.Builder builderTwo = new Clocking.Builder("Clocking one", 60, systemClockStub);

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
