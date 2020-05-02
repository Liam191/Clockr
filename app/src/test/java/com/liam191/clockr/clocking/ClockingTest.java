package com.liam191.clockr.clocking;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SimplifiableJUnitAssertion")
class ClockingTest {

    // TODO: Create better validation around Strings, ints, date ranges, etc.
    // TODO: Create automatic endTime (startTime plusHours(duration)?)
    //      TODO: Ability to set endTime manually
    //      TODO: Cannot set endTime and duration at the same time??

    // Clocking instantiation
    @Test
    void testCreateClocking_WithLabel(){
        Clocking workClocking = new Clocking.Builder("working", 10)
                .build();
        assertEquals(workClocking.label(), "working");
    }

    @Test
    void testCreateClocking_WithLabelAndTrimmedWhitespace(){
        Clocking workClocking = new Clocking.Builder("      working      ", 10)
                .build();
        assertEquals(workClocking.label(), "working");
    }

    @Test
    void testCreateClocking_ThrowsExceptionWithNullLabel(){
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new Clocking.Builder(null, 10).build()
        );
        assertTrue(e.getMessage().contains("label cannot be null"));
    }

    @Test
    void testCreateClocking_WithDescription(){
        Clocking workClocking = new Clocking.Builder("working", 20)
                .description("A work clocking")
                .build();
        assertEquals(workClocking.description(), "A work clocking");
    }

    @Test
    void testCreateClocking_WithDescriptionAndTrimmedWhitespace(){
        Clocking workClocking = new Clocking.Builder("working", 20)
                .description("         A work clocking          ")
                .build();
        assertEquals(workClocking.description(), "A work clocking");
    }

    @Test
    void testCreateClocking_ThrowsExceptionWithNullDescription(){
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new Clocking.Builder("working", 10)
                        .description(null)
                        .build()
        );
        assertTrue(e.getMessage().contains("description cannot be null"));
    }


    @Test
    void testCreateClocking_WithDurationInMinutes(){
        Clocking workClocking = new Clocking.Builder("working", 60)
                .build();
        assertEquals(workClocking.durationInMinutes(), 60);
    }

    @Test
    void testCreateClocking_ThrowsExceptionWithNegativeDurationInMinutes(){
        Exception e = assertThrows(IllegalArgumentException.class, () ->
            new Clocking.Builder("working", -720).build()
        );
        assertTrue(e.getMessage().contains("durationInMinutes cannot be zero or negative"));
    }

    @Test
    void testCreateClocking_WithStartTime(){
        Clocking workClocking = new Clocking.Builder("working", 70)
                .startTime(new Date(2020, 3, 1, 18 , 37, 50))
                .build();
        assertEquals(workClocking.startTime(), new Date(2020, 3, 1, 18 , 37, 50));
    }

    @Test
    void testCreateClocking_HasDefaultStartTime(){
        Clocking workClocking = new Clocking.Builder("working", 60)
                .build();
        assertEquals(workClocking.startTime().getTime(), new Date().getTime(), 100);
    }

    @Test
    void testCreateClocking_ThrowsExceptionWithNullStartTime(){
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                new Clocking.Builder("working", 60)
                        .startTime(null)
                        .build()
        );
        assertTrue(e.getMessage().contains("startTime cannot be null"));
    }

    @Test
    void testCreateClocking_HasImmutableStartTime(){
        Clocking clockingOne = new Clocking.Builder("working", 100)
                .build();
        Clocking clockingTwo = new Clocking.Builder("working", 100)
                .startTime(new Date())
                .build();

        Date clockingOneTimeRef = clockingOne.startTime();
        Date clockingTwoTimeRef = clockingTwo.startTime();

        clockingOneTimeRef.setYear(103);
        clockingTwoTimeRef.setYear(104);

        assertFalse(clockingOneTimeRef.equals(clockingOne.startTime()));
        assertFalse(clockingTwoTimeRef.equals(clockingTwo.startTime()));
    }




    // Clocking.equals()
    @SuppressWarnings("EqualsWithItself")
    @Test
    void testClockingEquals(){
        Date date = new Date(2020, 3, 3, 12 , 0, 0);

        Clocking clockingX = new Clocking.Builder("Same clocking", 60).startTime(date).build();
        Clocking clockingY = new Clocking.Builder("Same clocking", 60).startTime(date).build();
        Clocking clockingZ = new Clocking.Builder("Same clocking", 60).startTime(date).build();
        Clocking otherClocking = new Clocking.Builder("Different clocking", 120).startTime(date).build();

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
    void testClockingEquals_FromSameAndDifferentBuilders(){
        Date date = new Date(2020, 3, 3, 12 , 0, 0);
        Clocking.Builder builderOne = new Clocking.Builder("Clocking one", 60).startTime(date);
        Clocking.Builder builderTwo = new Clocking.Builder("Clocking one", 60).startTime(date);

        Clocking clockingA = builderOne.build();
        Clocking clockingB = builderOne.build();
        Clocking clockingX = builderTwo.build();
        Clocking clockingY = builderTwo.build();

        assertTrue(clockingA.equals(clockingB));
        assertTrue(clockingB.equals(clockingX));
        assertTrue(clockingX.equals(clockingY));
        assertTrue(clockingY.equals(clockingA));
    }

    @Test
    void testClockingEquals_WithDifferentLabels(){
        Date date = new Date(2020, 3, 3, 12 , 0, 0);
        Clocking clockingA = new Clocking.Builder("Label", 60).startTime(date).build();
        Clocking clockingB = new Clocking.Builder("Different label", 60).startTime(date).build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    void testClockingEquals_WithDifferentDescriptions(){
        Date date = new Date(2020, 3, 3, 12 , 0, 0);
        Clocking clockingA = new Clocking.Builder("Label", 60)
                .description("Hello, world description!")
                .startTime(date)
                .build();
        Clocking clockingB = new Clocking.Builder("Label", 60)
                .description("Goodbye, world description!")
                .startTime(date)
                .build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    void testClockingEquals_WithDifferentDurations(){
        Date date = new Date(2020, 3, 3, 12 , 0, 0);
        Clocking clockingA = new Clocking.Builder("Label", 200).startTime(date).build();
        Clocking clockingB = new Clocking.Builder("Label", 153).startTime(date).build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    void testClockingEquals_WithDifferentStartTimes(){
        Clocking clockingA = new Clocking.Builder("Label", 60)
                .startTime(new Date(2020, 3, 3, 12 , 0, 0))
                .build();
        Clocking clockingB = new Clocking.Builder("Label", 60)
                .startTime(new Date(2020, 3, 3, 14 , 59, 59))
                .build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }





    // Clocking.hashCode()
    @Test
    void testClockingHashCode(){
        Date date = new Date(2020, 3, 3, 12 , 0, 0);
        Clocking.Builder builderOne = new Clocking.Builder("Clocking one", 60).startTime(date);
        Clocking.Builder builderTwo = new Clocking.Builder("Clocking one", 60).startTime(date);

        Clocking clockingA = builderOne.build();
        Clocking clockingB = builderOne.build();
        Clocking clockingX = builderTwo.build();
        Clocking clockingY = builderTwo.build();

        assertEquals(clockingA.hashCode(), clockingB.hashCode());
        assertEquals(clockingB.hashCode(), clockingX.hashCode());
        assertEquals(clockingX.hashCode(), clockingY.hashCode());
        assertEquals(clockingY.hashCode(), clockingA.hashCode());
    }

    @Test
    void testClockingHashCode_FromSameAndDifferentBuilders(){
        Date date = new Date(2020, 3, 3, 12 , 0, 0);
        Clocking.Builder builderOne = new Clocking.Builder("Clocking one", 60).startTime(date);
        Clocking.Builder builderTwo = new Clocking.Builder("Clocking one", 60).startTime(date);

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
