package com.liam191.clockr;

public class Clocking {
    private String label;
    private String description;

    private Clocking(Builder clockingBuilder){
        this.label = clockingBuilder.label;
        this.description = clockingBuilder.description;
    }

    public String label(){
        return this.label;
    }

    public String description(){
        return this.description;
    }

    public static class Builder {
        private String label;
        private String description;

        public Builder(String label){
            this.label = label;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Clocking build(){
            return new Clocking(this);
        }
    }
}
