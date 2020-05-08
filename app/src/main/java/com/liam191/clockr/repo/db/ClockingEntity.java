package com.liam191.clockr.repo.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
class ClockingEntity {
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
}
