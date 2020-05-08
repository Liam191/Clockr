package com.liam191.clockr.repo.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.threeten.bp.OffsetDateTime;

import java.util.Objects;

@Entity
public class ClockingEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "label")
    public String label;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "duration_in_minutes")
    public int durationInMinutes;

    @ColumnInfo(name = "start_time")
    public OffsetDateTime startTime;

    @ColumnInfo(name = "end_time")
    public OffsetDateTime endTime;

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
                        startTime.equals(entity.startTime) &&
                        endTime.equals(entity.endTime));
    }

    @Override
    public int hashCode(){
        return Objects.hash(
                this.label,
                this.description,
                this.durationInMinutes,
                this.startTime,
                this.endTime);
    }
}
