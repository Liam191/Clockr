package com.liam191.clockr;

import java.time.LocalDateTime;

public final class Clocking {
    private String label;
    private String description;
    private double duration;
    private LocalDateTime fromTime;

    private Clocking(Builder clockingBuilder){
        this.label = clockingBuilder.label;
        this.description = clockingBuilder.description;
        this.duration = clockingBuilder.duration;
        this.fromTime = clockingBuilder.fromTime;
    }

    public String label(){
        return this.label;
    }

    public String description(){
        return this.description;
    }

    public double duration(){
        return this.duration;
    }

    public LocalDateTime fromTime(){
        return this.fromTime;
    }


    public static final class Builder {
        private String label;
        private String description;
        private double duration;
        private LocalDateTime fromTime;

        public Builder(String label){
            this.label = label;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder duration(double duration){
            this.duration = duration;
            return this;
        }

        public Builder fromTime(LocalDateTime fromTime){
            this.fromTime = fromTime;
            return this;
        }

        public Clocking build(){
            return new Clocking(this);
        }
    }
}
