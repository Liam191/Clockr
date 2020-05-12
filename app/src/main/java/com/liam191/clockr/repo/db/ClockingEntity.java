package com.liam191.clockr.repo.db;

import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ClockingEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "label")
    public String label;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "start_time")
    public String startTime;

    @ColumnInfo(name = "end_time")
    public String endTime;

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != getClass()){
            return false;
        }
        ClockingEntity entity = (ClockingEntity) o;
        return (
                label.equals(entity.label) &&
                description.equals(entity.description) &&
                startTime.equals(entity.startTime) &&
                endTime.equals(entity.endTime)
        );
    }

    @Override
    public int hashCode(){
        return Objects.hash(
                this.label,
                this.description,
                this.startTime,
                this.endTime);
    }

    @Override
    public String toString(){
        return "ClockingEntity { uid: "+ uid
                +", label: '"+ label
                +"', description: '"+ description
                +"', startTime: "+ startTime
                +", endTime: "+ endTime
                +" }";
    }
}
