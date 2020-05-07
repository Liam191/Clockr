package com.liam191.clockr.repo.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ClockingDao {
    @Insert
    void add(ClockingEntity clocking);

    @Query("SELECT * FROM clockingentity")
    List<ClockingEntity> getAll();

    @Query("DELETE FROM clockingentity")
    void clear();
}
