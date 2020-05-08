package com.liam191.clockr.repo.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.liam191.clockr.clocking.Clocking;

@Dao
public interface ClockingDao {
    @Insert
    @TypeConverters({ClockingConverter.class})
    public abstract void add(Clocking clocking);

    //@Query("SELECT * FROM clockingentity")
    //public abstract List<Clocking> getAll();

    @Query("DELETE FROM clockingentity")
    public abstract void clear();
}
