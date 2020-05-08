package com.liam191.clockr.repo.db;

import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ClockingEntity {
    @PrimaryKey(autoGenerate = true)
    int uid;

    @ColumnInfo(name = "label")
    String label;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "duration_in_minutes")
    int durationInMinutes;

    @ColumnInfo(name = "start_time")
    Long startTime;

    @Override
    public boolean equals(Object o){
        if(o == null || o.getClass() != getClass()){
            return false;
        }
        ClockingEntity entity = (ClockingEntity) o;
        return (
                label.equals(entity.label) &&
                        description.equals(entity.description) &&
                        durationInMinutes == entity.durationInMinutes &&
                        startTime.equals(entity.startTime));
    }

    @Override
    public int hashCode(){
        return Objects.hash(
                this.label,
                this.description,
                this.durationInMinutes,
                this.startTime);
    }
}
