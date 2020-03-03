package com.liam191.clockr.clocking;

import java.time.Clock;
import java.time.LocalDateTime;

public final class Clocking {
    private final String label;
    private final String description;
    private final int durationInMinutes;
    private final LocalDateTime startTime;

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

    public LocalDateTime startTime(){
        return this.startTime;
    }


    public static final class Builder {
        private String label;
        private String description;
        private int durationInMinutes;
        private LocalDateTime startTime;
        private Clock systemClock;

        public Builder(String label){
            this.label = label;
        }

        Builder(String label, Clock systemClock){
            this.label = label;
            this.systemClock = systemClock;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder durationInMinutes(int durationInMinutes){
            this.durationInMinutes = durationInMinutes;
            return this;
        }

        public Builder startTime(LocalDateTime fromTime){
            this.startTime = fromTime;
            return this;
        }

        public Clocking build(){
            if (this.startTime == null) {
                this.startTime = (this.systemClock == null) ?
                    LocalDateTime.now() : LocalDateTime.now(systemClock);
            }
            return new Clocking(this);
        }
    }
}
