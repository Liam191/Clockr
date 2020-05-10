package com.liam191.clockr.clocking;

import org.threeten.bp.Clock;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.Objects;

public final class Clocking {
    private final String label;
    private final String description;
    private final ZonedDateTime startTime;
    private final ZonedDateTime endTime;

    private Clocking(Builder clockingBuilder){
        this.label = clockingBuilder.label;
        this.description = clockingBuilder.description;
        this.startTime = clockingBuilder.startTime;
        this.endTime = clockingBuilder.endTime;
    }

    public String label(){
        return this.label;
    }

    public String description(){
        return this.description;
    }

    public int durationInMinutes(){
        return (int)ChronoUnit.MINUTES.between(startTime, endTime);
    }

    public ZonedDateTime startTime(){
        return this.startTime;
    }

    public ZonedDateTime endTime(){
        return this.endTime;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != getClass()){
            return false;
        }
        Clocking clocking = (Clocking) o;

        return (
            label.equals(clocking.label) &&
            description.equals(clocking.description) &&
            startTime.equals(clocking.startTime) &&
            endTime.equals(clocking.endTime));
    }

    @Override
    public int hashCode(){
        return Objects.hash(
                this.label,
                this.description,
                this.startTime,
                this.endTime);
    }



    public static final class Builder {
        private static final int DEFAULT_DURATION_IN_MINS = 30;
        private Clock clock = Clock.systemDefaultZone();
        private String label = "";
        private String description = "";
        // start and end times do *not* get a default time value here as the time should
        // be set when a ClockingEntity is built, not when the Builder is created.
        private ZonedDateTime startTime = null;
        private ZonedDateTime endTime = null;

        public Builder(String label){
            if(label == null || label.trim().length() == 0) {
                throw new IllegalArgumentException("label cannot be empty or null");
            }
            this.label = label.trim();
        }

        Builder(String label, Clock clock){
            this(label);
            this.clock = clock;
        }

        public Builder(String label, ZonedDateTime startTime){
            this(label);
            startTime(startTime);
        }

        public Builder(String label, ZonedDateTime startTime, ZonedDateTime endTime){
            this(label);
            startTime(startTime);
            endTime(endTime);
        }


        public Builder description(String description) throws IllegalArgumentException {
            if(description == null || description.trim().length() == 0) {
                throw new IllegalArgumentException("description cannot be empty or null");
            }
            this.description = description.trim();
            return this;
        }

        public Builder startTime(ZonedDateTime startTime) throws IllegalArgumentException {
            if(startTime == null) {
                throw new IllegalArgumentException("startTime cannot be null");
            }
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(ZonedDateTime endTime) throws IllegalArgumentException {
            if(endTime == null) {
                throw new IllegalArgumentException("endTime cannot be null");
            }
            this.endTime = endTime;
            return this;
        }


        public Clocking build(){
            this.startTime = (startTime == null) ?
                    ZonedDateTime.now(clock) : startTime;

            this.endTime = (endTime == null) ?
                    startTime.plusMinutes(DEFAULT_DURATION_IN_MINS) : endTime;

            if(endTime.isBefore(startTime)){
                throw new IllegalArgumentException("endTime cannot be before startTime");
            }
            return new Clocking(this);
        }
    }
}
