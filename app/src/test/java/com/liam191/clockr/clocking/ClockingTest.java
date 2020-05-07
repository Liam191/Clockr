package com.liam191.clockr.clocking;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("SimplifiableJUnitAssertion")
public class ClockingTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    // TODO: Create better validation around Strings, ints, date ranges, etc.
    // TODO: Create automatic endTime (startTime plusHours(duration)?)
    //      TODO: Ability to set endTime manually
    //      TODO: Cannot set endTime and duration at the same time??

    // ClockingEntity instantiation
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
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("label cannot be null");

        new Clocking.Builder(null, 10).build();
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
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("description cannot be null");

        new Clocking.Builder("working", 10)
                .description(null)
                .build();
    }


    @Test
    public void testCreateClocking_WithDurationInMinutes(){
        Clocking workClocking = new Clocking.Builder("working", 60)
                .build();
        assertEquals(workClocking.durationInMinutes(), 60);
    }

    @Test
    public void testCreateClocking_ThrowsExceptionWithNegativeDurationInMinutes(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("durationInMinutes cannot be zero or negative");

        new Clocking.Builder("working", -720).build();
    }

    @Test
    public void testCreateClocking_WithStartTime(){
        Clocking workClocking = new Clocking.Builder("working", 70)
                .startTime(new Date(2020 - 1900, 3, 1, 18 , 37, 50))
                .build();
        assertEquals(workClocking.startTime(), new Date(2020 - 1900, 3, 1, 18 , 37, 50));
    }

    @Test
    public void testCreateClocking_HasDefaultStartTime(){
        Clocking workClocking = new Clocking.Builder("working", 60)
                .build();
        assertEquals(workClocking.startTime().getTime(), new Date().getTime(), 100);
    }

    @Test
    public void testCreateClocking_ThrowsExceptionWithNullStartTime(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("startTime cannot be null");

        new Clocking.Builder("working", 60)
                .startTime(null)
                .build();
    }

    @Test
    public void testCreateClocking_HasImmutableDefaultStartTime(){
        Clocking clocking = new Clocking.Builder("working", 100)
                .build();

        Date clockingStartTime = clocking.startTime();

        clockingStartTime.setYear(103);

        assertFalse(clockingStartTime.equals(clocking.startTime()));
    }

    @Test
    public void testCreateClocking_HasImmutableStartTimeWithBuilder(){
        Date originalClockingStartTime = new Date(2020 - 1900, 10, 10);
        Clocking clocking = new Clocking.Builder("working", 100)
                .startTime(originalClockingStartTime)
                .build();

        originalClockingStartTime.setYear(103);

        assertFalse(originalClockingStartTime.equals(clocking.startTime()));
    }




    // ClockingEntity.equals()
    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testClockingEquals(){
        Date date = new Date(2020 - 1900, 3, 3, 12 , 0, 0);

        Clocking clockingX = new Clocking.Builder("Same clocking", 60, date).build();
        Clocking clockingY = new Clocking.Builder("Same clocking", 60, date).build();
        Clocking clockingZ = new Clocking.Builder("Same clocking", 60, date).build();
        Clocking otherClocking = new Clocking.Builder("Different clocking", 120, date).build();

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
    public void testClockingEquals_FromSameAndDifferentBuilders(){
        Date date = new Date(2020 - 1900, 3, 3, 12 , 0, 0);
        Clocking.Builder builderOne = new Clocking.Builder("ClockingEntity one", 60, date);
        Clocking.Builder builderTwo = new Clocking.Builder("ClockingEntity one", 60, date);

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
    public void testClockingEquals_WithDifferentLabels(){
        Date date = new Date(2020 - 1900, 3, 3, 12 , 0, 0);
        Clocking clockingA = new Clocking.Builder("Label", 60, date).build();
        Clocking clockingB = new Clocking.Builder("Different label", 60, date).build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    public void testClockingEquals_WithDifferentDescriptions(){
        Date date = new Date(2020 - 1900, 3, 3, 12 , 0, 0);
        Clocking clockingA = new Clocking.Builder("Label", 60, date)
                .description("Hello, world description!")
                .build();
        Clocking clockingB = new Clocking.Builder("Label", 60, date)
                .description("Goodbye, world description!")
                .build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    public void testClockingEquals_WithDifferentDurations(){
        Date date = new Date(2020 - 1900, 3, 3, 12 , 0, 0);
        Clocking clockingA = new Clocking.Builder("Label", 200, date).build();
        Clocking clockingB = new Clocking.Builder("Label", 153, date).build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    public void testClockingEquals_WithDifferentStartTimes(){
        Clocking clockingA = new Clocking.Builder("Label", 60)
                .startTime(new Date(2020 - 1900, 3, 3, 12 , 0, 0))
                .build();
        Clocking clockingB = new Clocking.Builder("Label", 60)
                .startTime(new Date(2020 - 1900, 3, 3, 14 , 59, 59))
                .build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }




    // ClockingEntity.hashCode()
    @Test
    public void testClockingHashCode(){
        Date date = new Date(2020 - 1900, 3, 3, 12 , 0, 0);
        Clocking.Builder builderOne = new Clocking.Builder("ClockingEntity one", 60).startTime(date);
        Clocking.Builder builderTwo = new Clocking.Builder("ClockingEntity one", 60).startTime(date);

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
    public void testClockingHashCode_FromSameAndDifferentBuilders(){
        Date date = new Date(2020 - 1900, 3, 3, 12 , 0, 0);
        Clocking.Builder builderOne = new Clocking.Builder("ClockingEntity one", 60).startTime(date);
        Clocking.Builder builderTwo = new Clocking.Builder("ClockingEntity one", 60).startTime(date);

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
