package com.liam191.clockr.repo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ClockingEntity.class}, version = 2)
@TypeConverters({ClockingConverter.class})
public abstract class ClockrDatabase extends RoomDatabase {
    public abstract ClockingDao clockingDao();
}
