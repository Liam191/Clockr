package com.liam191.clockr;

class Clocking {
    private String label;
    private String description;
    private double duration;


    private Clocking(Builder clockingBuilder){
        this.label = clockingBuilder.label;
        this.description = clockingBuilder.description;
        this.duration = clockingBuilder.duration;
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

    public static class Builder {
        private String label;
        private String description;
        private double duration;

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

        public Clocking build(){
            return new Clocking(this);
        }
    }
}
