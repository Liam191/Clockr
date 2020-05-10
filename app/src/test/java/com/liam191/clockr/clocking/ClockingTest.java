package com.liam191.clockr.clocking;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.threeten.bp.Clock;
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
    //          - validate that endTime is after startTime

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
    public void testLabel_WithNullThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("label cannot be empty or null");
        new Clocking.Builder(null).build();
    }

    @Test
    public void testLabel_WithEmptyThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("label cannot be empty or null");
        new Clocking.Builder("").build();
    }

    @Test
    public void testLabel_WithJustWhitespaceThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("label cannot be empty or null");
        new Clocking.Builder("            ").build();
    }


    // Clocking description
    @Test
    public void testDescription(){
        Clocking workClocking = new Clocking.Builder("working")
                .description("A work clocking")
                .build();
        assertEquals(workClocking.description(), "A work clocking");
    }

    @Test
    public void testDescription_WithWhitespace(){
        Clocking workClocking = new Clocking.Builder("working")
                .description("         A work clocking          ")
                .build();
        assertEquals(workClocking.description(), "A work clocking");
    }

    @Test
    public void testDescription_WithNullThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("description cannot be empty or null");
        new Clocking.Builder("working")
                .description(null)
                .build();
    }

    @Test
    public void testDescription_WithEmptyThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("description cannot be empty or null");
        new Clocking.Builder("working")
                .description("")
                .build();
    }

    @Test
    public void testDescription_WithJustWhitespaceThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("description cannot be empty or null");
        new Clocking.Builder("working")
                .description("               ")
                .build();
    }


    // Clocking duration
    @Test
    public void testDurationInMinutes_HasDefault(){
        Clocking workClocking = new Clocking.Builder("working").build();
        assertEquals(workClocking.durationInMinutes(), 30);
    }

    @Test
    public void testDurationInMinutes_WithStartAndEndTimes(){
        int expectedDuration = 123;
        ZonedDateTime startTime = ZonedDateTime.of(2020, 1, 3, 0,0,0,0,ZoneId.systemDefault());
        ZonedDateTime endTime = startTime.plusMinutes(expectedDuration);

        Clocking workClocking = new Clocking.Builder("working", startTime, endTime).build();
        assertEquals(workClocking.durationInMinutes(), expectedDuration);
    }



    @Test
    public void testCreateClocking_WithStartTime(){
        ZonedDateTime expectedStartTime = ZonedDateTime.of(2020, 3, 1, 18 , 37, 50,0,ZoneId.systemDefault());
        Clocking workClocking = new Clocking.Builder("working")
                .startTime(expectedStartTime)
                .build();
        assertEquals(workClocking.startTime(), expectedStartTime);
    }

    @Test
    public void testCreateClocking_HasDefaultStartTime(){
        ZonedDateTime expectedDate = ZonedDateTime.of(2020, 10, 11, 17,2,3,0, ZoneId.systemDefault());
        Clock testClock = Clock.fixed(expectedDate.toInstant(), expectedDate.getZone());

        Clocking workClocking = new Clocking.Builder("working", testClock)
                .build();
        assertEquals(workClocking.startTime(), expectedDate);
    }

    @Test
    public void testCreateClocking_ThrowsExceptionWithNullStartTimeLocal(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("startTime cannot be null");

        ZonedDateTime nullTime = null;
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
        ZonedDateTime expectedEndTime = ZonedDateTime.of(2020, 3, 1, 18 , 37, 50,0,ZoneId.systemDefault());
        Clocking workClocking = new Clocking.Builder("working")
                .endTime(expectedEndTime)
                .build();
        assertEquals(workClocking.endTime(), expectedEndTime);
    }

    @Test
    public void testCreateClocking_HasDefaultEndTime(){
        ZonedDateTime expectedDate = ZonedDateTime.of(2020, 10, 11, 17,2,3,0, ZoneId.systemDefault());
        Clock testClock = Clock.fixed(expectedDate.toInstant(), expectedDate.getZone());

        Clocking workClocking = new Clocking.Builder("working", testClock)
                .build();
        assertEquals(workClocking.endTime(), expectedDate.plusMinutes(30));
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
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());

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
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());
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
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());
        Clocking clockingA = new Clocking.Builder("Label", date).build();
        Clocking clockingB = new Clocking.Builder("Different label", date).build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    public void testClockingEquals_WithDifferentDescriptions(){
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());
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
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());
        Clocking clockingA = new Clocking.Builder("Label", date, date.plusMinutes(15)).build();
        Clocking clockingB = new Clocking.Builder("Label", date, date.plusMinutes(16)).build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    public void testClockingEquals_WithDifferentStartTimes(){

        Clocking clockingA = new Clocking.Builder("Label")
                .startTime(ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault()))
                .build();
        Clocking clockingB = new Clocking.Builder("Label")
                .startTime(ZonedDateTime.of(2020, 3, 3, 14 , 59, 59,0,ZoneId.systemDefault()))
                .build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }




    // ClockingEntity.hashCode()
    @Test
    public void testClockingHashCode(){
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());
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
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());
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
