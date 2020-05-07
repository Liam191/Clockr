package com.liam191.clockr.repo.db;

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

    @ColumnInfo(name = "duration_in_minutes")
    public int durationInMinutes;

    //@ColumnInfo(name = "start_time")
    //public Date startTime;
}
