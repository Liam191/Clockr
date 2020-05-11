package com.liam191.clockr.clocking;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.threeten.bp.Clock;
import org.threeten.bp.Duration;
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
        assertEquals(workClocking.durationInMinutes(), Duration.ofMinutes(30));
    }

    @Test
    public void testDurationInMinutes_WithStartAndEndTimes(){
        Duration expectedDuration = Duration.ofMinutes(123);
        ZonedDateTime startTime = ZonedDateTime.parse("2020-01-03T00:00+00:00[Europe/London]");
        ZonedDateTime endTime = startTime.plusMinutes(expectedDuration.toMinutes());

        Clocking workClocking = new Clocking.Builder("working")
                .startTime(startTime)
                .endTime(endTime)
                .build();
        assertEquals(workClocking.durationInMinutes(), expectedDuration);
    }

    @Test
    public void testDurationInMinutes_WithStartAndEndTimesBackwardsOrder(){
        Duration expectedDuration = Duration.ofMinutes(123);
        ZonedDateTime startTime = ZonedDateTime.parse("2020-01-03T00:00+00:00[Europe/London]");
        ZonedDateTime endTime = startTime.plusMinutes(expectedDuration.toMinutes());

        Clocking workClocking = new Clocking.Builder("working")
                .endTime(endTime)
                .startTime(startTime)
                .build();
        assertEquals(workClocking.durationInMinutes(), expectedDuration);
    }



    // CLocking startTime
    @Test
    public void testStartTime(){
        ZonedDateTime expectedStartTime = ZonedDateTime.of(2020, 3, 1, 18 , 37, 50,0,ZoneId.systemDefault());
        Clocking workClocking = new Clocking.Builder("working")
                .startTime(expectedStartTime)
                .build();
        assertEquals(workClocking.startTime(), expectedStartTime);
    }

    @Test
    public void testStartTime_HasDefault(){
        ZonedDateTime expectedDate = ZonedDateTime.of(2020, 10, 11, 17,2,3,0, ZoneId.systemDefault());
        Clock testClock = Clock.fixed(expectedDate.toInstant(), expectedDate.getZone());

        Clocking workClocking = new Clocking.Builder("working", testClock)
                .build();
        assertEquals(workClocking.startTime(), expectedDate);
    }

    @Test
    public void testStartTime_HasDefaultWithEndTime(){
        ZonedDateTime expectedDate = ZonedDateTime.of(2020, 10, 11, 17,2,3,0, ZoneId.systemDefault());
        Clock testClock = Clock.fixed(expectedDate.toInstant(), expectedDate.getZone());

        Clocking workClocking = new Clocking.Builder("working", testClock)
                .endTime(expectedDate.plusMinutes(30))
                .build();
        assertEquals(workClocking.startTime(), expectedDate);
    }

    @Test
    public void testStartTime_WithNullThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("startTime cannot be null");

        ZonedDateTime nullTime = null;
        new Clocking.Builder("working")
                .startTime(nullTime)
                .build();
    }

    @Test
    public void testStartTime_WithJustEndTimeHasDefaultStartTimeBefore(){
        ZonedDateTime expectedEndDate = ZonedDateTime.parse("2020-09-09T10:00+01:00[Europe/London]");
        ZonedDateTime expectedStartDate = expectedEndDate.minusMinutes(30);

        Clock testClock = Clock.fixed(ZonedDateTime.of(2020, 11, 23, 17,46,0,0, ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        Clocking clocking = new Clocking.Builder("working", testClock)
                .endTime(expectedEndDate)
                .build();
        assertEquals(clocking.startTime(), expectedStartDate);
    }



    // Clocking endTime
    @Test
    public void testEndTime(){
        ZonedDateTime expectedStartDate = ZonedDateTime.of(2020, 10, 11, 17,2,3,0, ZoneId.systemDefault());
        ZonedDateTime expectedEndDate = expectedStartDate.plusMinutes(5);
        Clock testClock = Clock.fixed(expectedStartDate.toInstant(), expectedStartDate.getZone());

        Clocking workClocking = new Clocking.Builder("working", testClock)
                .endTime(expectedEndDate)
                .build();
        assertEquals(workClocking.endTime(), expectedEndDate);
    }

    @Test
    public void testEndTime_HasDefault(){
        ZonedDateTime expectedDate = ZonedDateTime.of(2020, 10, 11, 17,2,3,0, ZoneId.systemDefault());
        Clock testClock = Clock.fixed(expectedDate.toInstant(), expectedDate.getZone());

        Clocking workClocking = new Clocking.Builder("working", testClock)
                .build();
        assertEquals(workClocking.endTime(), expectedDate.plusMinutes(30));
    }


    @Test
    public void testEndTime_WithNullThrowsException(){
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("endTime cannot be null");

        ZonedDateTime nullTime = null;
        new Clocking.Builder("working")
                .endTime(nullTime)
                .build();
    }



    // Clocking equals
    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals(){
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());

        Clocking clockingX = new Clocking.Builder("Same clocking").startTime(date).build();
        Clocking clockingY = new Clocking.Builder("Same clocking").endTime(date.plusMinutes(30)).build();
        Clocking clockingZ = new Clocking.Builder("Same clocking").startTime(date).build();
        Clocking otherClocking = new Clocking.Builder("Different clocking").startTime(date).build();

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
    public void testEquals_FromSameAndDifferentBuilders(){
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());
        Clocking.Builder builderOne = new Clocking.Builder("ClockingEntity one").startTime(date);
        Clocking.Builder builderTwo = new Clocking.Builder("ClockingEntity one").endTime(date.plusMinutes(30));

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
    public void testEquals_WithDifferentLabels(){
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());
        Clocking clockingA = new Clocking.Builder("Label").startTime(date).build();
        Clocking clockingB = new Clocking.Builder("Different label").startTime(date).build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    public void testEquals_WithDifferentDescriptions(){
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());
        Clocking clockingA = new Clocking.Builder("Label").startTime(date)
                .description("Hello, world description!")
                .build();
        Clocking clockingB = new Clocking.Builder("Label").startTime(date)
                .description("Goodbye, world description!")
                .build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    public void testEquals_WithDifferentDurations(){
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());
        Clocking clockingA = new Clocking.Builder("Label")
                .startTime(date)
                .endTime(date.plusMinutes(15))
                .build();
        Clocking clockingB = new Clocking.Builder("Label")
                .startTime(date)
                .endTime(date.plusMinutes(16))
                .build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }

    @Test
    public void testEquals_WithDifferentStartTimes(){

        Clocking clockingA = new Clocking.Builder("Label")
                .startTime(ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault()))
                .build();
        Clocking clockingB = new Clocking.Builder("Label")
                .startTime(ZonedDateTime.of(2020, 3, 3, 14 , 59, 59,0,ZoneId.systemDefault()))
                .build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }


    @Test
    public void testEquals_WithDifferentEndTimes(){
        ZonedDateTime startDate = ZonedDateTime.of(2020, 3, 3, 10,0,0,0, ZoneId.systemDefault());
        Clock testClock = Clock.fixed(startDate.toInstant(), startDate.getZone());

        Clocking clockingA = new Clocking.Builder("Label",testClock)
                .endTime(ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault()))
                .build();
        Clocking clockingB = new Clocking.Builder("Label",testClock)
                .endTime(ZonedDateTime.of(2020, 3, 3, 14 , 59, 59,0,ZoneId.systemDefault()))
                .build();

        assertFalse(clockingA.equals(clockingB));
        assertFalse(clockingB.equals(clockingA));
    }



    // Clocking hashCode
    @Test
    public void testHashCode(){
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());
        Clocking.Builder builderOne = new Clocking.Builder("ClockingEntity one").startTime(date);
        Clocking.Builder builderTwo = new Clocking.Builder("ClockingEntity one").endTime(date.plusMinutes(30));

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
    public void testHashCode_FromSameAndDifferentBuilders(){
        ZonedDateTime date = ZonedDateTime.of(2020, 3, 3, 12 , 0, 0,0,ZoneId.systemDefault());
        Clocking.Builder builderOne = new Clocking.Builder("ClockingEntity one").startTime(date);
        Clocking.Builder builderTwo = new Clocking.Builder("ClockingEntity one").endTime(date.plusMinutes(30));

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
