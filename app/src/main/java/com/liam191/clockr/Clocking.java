package com.liam191.clockr;

public class Clocking {
    public String label;
    public Clocking(String label){
        this.label = label;
    }

    public String label(){
        return label;
    }

    public static class Builder {
        private String label;
        public Builder(String label){
            this.label = label;
        }

        public Clocking build(){
            return new Clocking(label);
        }
    }
}
