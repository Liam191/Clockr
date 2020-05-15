package com.liam191.clockr.repo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ClockingEntity.class}, version = 1)
public abstract class ClockrDatabase extends RoomDatabase {
    public abstract ClockingDao clockingDao();
    public abstract ClockingDayDao clockingDayDao();
}
