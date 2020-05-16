package com.liam191.clockr.repo.db;

import org.threeten.bp.ZonedDateTime;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

@Dao
public interface ClockingDao {
    @Insert
    void insert(ClockingEntity clockingEntity);

    // Deletes the *first* entry that matches the given fields
    @Query("DELETE FROM clockingentity WHERE uid IN (" +
                "SELECT uid FROM clockingentity " +
                "WHERE label = :label " +
                    "AND description = :description " +
                    "AND start_time = :startTime " +
                    "AND end_time = :endTime " +
                "LIMIT 1" +
            ")")
    @TypeConverters(ZonedDateTimeConverter.class)
    void delete(String label, String description, ZonedDateTime startTime, ZonedDateTime endTime);

    @Query("SELECT * FROM clockingentity")
    List<ClockingEntity> getAll();
}
