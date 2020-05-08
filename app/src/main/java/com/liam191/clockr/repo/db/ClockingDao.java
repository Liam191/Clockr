package com.liam191.clockr.repo.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.List;

@Dao
@TypeConverters({})
public interface ClockingDao {
    @Insert
    public abstract void add(ClockingEntity clocking);

    @Query("SELECT * FROM clockingentity")
    public abstract List<ClockingEntity> getAll();

    @Query("DELETE FROM clockingentity")
    public abstract void clear();
}
