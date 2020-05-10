package com.liam191.clockr.clocking;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.threeten.bp.Clock;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

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

    // Clocking label
    @Test
    public void testLabel(){
        Clocking workClocking = new Clocking.Builder("working")
                .build();
        assertEquals(workClocking.label(), "working");
    }

    @Test
    public void testLabel_WithWhitespace(){
        Clocking workClocking = new Clocking.Builder("      working      ")
                .build();
        assertEquals(workClocking.label(), "working");
    }

    @Test
    public void testLabel_WithNullLabelThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("label cannot be empty or null");

        new Clocking.Builder(null).build();
    }

    @Test
    public void testLabel_WithEmptyLabelThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("label cannot be empty or null");

        new Clocking.Builder("").build();
    }

    @Test
    public void testLabel_WithWhitespaceThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("label cannot be empty or null");

        new Clocking.Builder("            ").build();
    }


    // Clocking description
    @Test
    public void testCreateClocking_WithDescription(){
        Clocking workClocking = new Clocking.Builder("working")
                .description("A work clocking")
                .build();
        assertEquals(workClocking.description(), "A work clocking");
    }

    @Test
    public void testCreateClocking_WithDescriptionAndWhitespace(){
        Clocking workClocking = new Clocking.Builder("working")
                .description("         A work clocking          ")
                .build();
        assertEquals(workClocking.description(), "A work clocking");
    }

    @Test
    public void testCreateClocking_ThrowsExceptionWithNullDescription(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("description cannot be null");

        new Clocking.Builder("working")
                .description(null)
                .build();
    }


    @Test
    public void testCreateClocking_HasDefaultDurationInMinutes(){
        Clocking workClocking = new Clocking.Builder("working").build();
        assertEquals(workClocking.durationInMinutes(), 30);
    }

    @Test
    public void testCreateClocking_HasCorrectDurationInMinutes(){
        int expectedDuration = 123;
        LocalDateTime startTime = LocalDateTime.of(2020, 01, 03, 0,0,0);
        LocalDateTime endTime = startTime.plusMinutes(expectedDuration);

        Clocking workClocking = new Clocking.Builder("working", startTime, endTime).build();
        assertEquals(workClocking.durationInMinutes(), expectedDuration);
    }



    @Test
    public void testCreateClocking_WithStartTime(){
        Clocking workClocking = new Clocking.Builder("working")
                .startTime(LocalDateTime.of(2020, 3, 1, 18 , 37, 50))
                .build();
        assertEquals(workClocking.startTime().toLocalDateTime(), LocalDateTime.of(2020, 3, 1, 18 , 37, 50));
    }

    @Test
    public void testCreateClocking_HasDefaultStartTime(){
        Clocking workClocking = new Clocking.Builder("working")
                .build();
        assertEquals(workClocking.startTime().toLocalDateTime(), LocalDateTime.now());
    }

    @Test
    public void testCreateClocking_ThrowsExceptionWithNullStartTimeLocal(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("startTime cannot be null");

        LocalDateTime nullTime = null;
        new Clocking.Builder("working")
                .startTime(nullTime)
                .build();
    }

    @Test
    public void testCreateClocking_ThrowsExceptionWithNullStartTimeZoned(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("startTime cannot be null");

        ZonedDateTime nullTime = null;
        new Clocking.Builder("working")
                .startTime(nullTime)
                .build();
    }

    // endTime
    @Test
    public void testCreateClocking_WithEndTime(){
        Clocking workClocking = new Clocking.Builder("working")
                .endTime(LocalDateTime.of(2020, 3, 1, 18 , 37, 50))
                .build();
        assertEquals(workClocking.endTime().toLocalDateTime(), LocalDateTime.of(2020, 3, 1, 18 , 37, 50));
    }

    @Test
    public void testCreateClocking_HasDefaultEndTime(){
        ZonedDateTime expectedDate = ZonedDateTime.of(LocalDateTime.of(2020, 10, 11, 17,2,3), ZoneId.systemDefault());
        Clock testClock = Clock.fixed(expectedDate.toInstant(), expectedDate.getZone());

        Clocking workClocking = new Clocking.Builder("working", testClock)
                .build();
        assertEquals(workClocking.endTime().toLocalDateTime(), expectedDate.plusMinutes(30).toLocalDateTime());
    }
/*
    @Test
    public void testCreateClocking_ThrowsExceptionWithNullEndTime(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("startTime cannot be null");

        new Clocking.Builder("working", 60)
                .endTime(null)
                .build();
    }

    @Test
    public void testCreateClocking_HasImmutableDefaultEndTime(){
        Clocking clocking = new Clocking.Builder("working", 100)
                .build();

        LocalDateTime clockingStartTime = clocking.startTime();

        clockingStartTime.setYear(103);

        assertFalse(clockingStartTime.equals(clocking.startTime()));
    }

    @Test
    public void testCreateClocking_HasImmutableEndTimeWithBuilder(){
        LocalDateTime originalClockingStartTime = LocalDateTime.of(2020, 10, 10);
        Clocking clocking = new Clocking.Builder("working", 100)
                .startTime(originalClockingStartTime)
                .build();

        originalClockingStartTime.setYear(103);

        assertFalse(originalClockingStartTime.equals(clocking.startTime()));
    }
*/


    // ClockingEntity.equals()
    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testClockingEquals(){
        LocalDateTime date = LocalDateTime.of(2020, 3, 3, 12 , 0, 0);

        Clocking clockingX = new Clocking.Builder("Same clocking", date).build();
        Clocking clockingY = new Clocking.Builder("Same clocking", date).build();
        Clocking clockingZ = new Clocking.Builder("Same clocking", date).build();
        Clocking otherClocking = new Clocking.Builder("Different clocking", date).build();

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
        LocalDateTime date = LocalDateTime.of(2020 , 3, 3, 12 , 0, 0);
        Clocking.Builder builderOne = new Clocking.Builder("ClockingEntity one", date);
        Clocking.Builder builderTwo = new Clocking.Builder("ClockingEntity one", date);

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
        LocalDateTime date = LocalDateTime.of(2020, 3, 3, 12 , 0, 0);
        Clocking clockingA = new Clocking.Builder("Label", date).build();
        Clocking clockingB = new Clocking.Builder("Different label", date).build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    public void testClockingEquals_WithDifferentDescriptions(){
        LocalDateTime date = LocalDateTime.of(2020, 3, 3, 12 , 0, 0);
        Clocking clockingA = new Clocking.Builder("Label", date)
                .description("Hello, world description!")
                .build();
        Clocking clockingB = new Clocking.Builder("Label", date)
                .description("Goodbye, world description!")
                .build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    public void testClockingEquals_WithDifferentDurations(){
        LocalDateTime date = LocalDateTime.of(2020, 3, 3, 12 , 0, 0);
        Clocking clockingA = new Clocking.Builder("Label", date, date.plusMinutes(15)).build();
        Clocking clockingB = new Clocking.Builder("Label", date, date.plusMinutes(16)).build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    public void testClockingEquals_WithDifferentStartTimes(){
        Clocking clockingA = new Clocking.Builder("Label")
                .startTime(LocalDateTime.of(2020, 3, 3, 12 , 0, 0))
                .build();
        Clocking clockingB = new Clocking.Builder("Label")
                .startTime(LocalDateTime.of(2020, 3, 3, 14 , 59, 59))
                .build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }




    // ClockingEntity.hashCode()
    @Test
    public void testClockingHashCode(){
        LocalDateTime date = LocalDateTime.of(2020, 3, 3, 12 , 0, 0);
        Clocking.Builder builderOne = new Clocking.Builder("ClockingEntity one").startTime(date);
        Clocking.Builder builderTwo = new Clocking.Builder("ClockingEntity one").startTime(date);

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
        LocalDateTime date = LocalDateTime.of(2020, 3, 3, 12 , 0, 0);
        Clocking.Builder builderOne = new Clocking.Builder("ClockingEntity one").startTime(date);
        Clocking.Builder builderTwo = new Clocking.Builder("ClockingEntity one").startTime(date);

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
