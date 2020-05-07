package com.liam191.clockr.clocking;

import java.util.Date;
import java.util.Objects;

public final class Clocking {
    private final String label;
    private final String description;
    private final int durationInMinutes;
    private final Date startTime;

    private Clocking(Builder clockingBuilder){
        this.label = clockingBuilder.label;
        this.description = clockingBuilder.description;
        this.durationInMinutes = clockingBuilder.durationInMinutes;
        this.startTime = clockingBuilder.startTime;
    }

    public String label(){
        return this.label;
    }

    public String description(){
        return this.description;
    }

    public int durationInMinutes(){
        return this.durationInMinutes;
    }

    public Date startTime(){
        return (Date)(this.startTime.clone());
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
            durationInMinutes == clocking.durationInMinutes &&
            startTime.equals(clocking.startTime));
    }

    @Override
    public int hashCode(){
        return Objects.hash(
                this.label,
                this.description,
                this.durationInMinutes,
                this.startTime);
    }

    public static final class Builder {
        private String label = "";
        private String description = "";
        private int durationInMinutes = 0;
        // startTime does *not* get a default time value as the time should be set
        // when a ClockingEntity is created, not when the Builder is created.
        private Date startTime = null;

        public Builder(String label, int durationInMinutes){
            if(label == null) {
                throw new IllegalArgumentException("label cannot be null");
            }
            this.label = label.trim();

            if(durationInMinutes < 0) {
                throw new IllegalArgumentException("durationInMinutes cannot be zero or negative");
            }
            this.durationInMinutes = durationInMinutes;
        }

        public Builder(String label, int durationInMinutes, Date startTime){
            this(label, durationInMinutes);
            this.startTime(startTime);
        }


        public Builder description(String description) throws IllegalArgumentException {
            if(description == null) {
                throw new IllegalArgumentException("description cannot be null");
            }
            this.description = description.trim();
            return this;
        }

        public Builder startTime(Date startTime) throws IllegalArgumentException {
            if(startTime == null) {
                throw new IllegalArgumentException("startTime cannot be null");
            }
            this.startTime = (Date)(startTime.clone());
            return this;
        }

        public Clocking build(){
            if (startTime == null) {
                this.startTime = new Date();
            }
            return new Clocking(this);
        }
    }
}
