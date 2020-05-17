package com.liam191.clockr.repo.db;

import org.threeten.bp.ZonedDateTime;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

@Dao
public abstract class ClockingDao {
    @Insert
    public abstract void insert(ClockingEntity clockingEntity);

    public void delete(ClockingEntity entity){
        delete(entity.label, entity.description, entity.startTime, entity.endTime);
    }

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
    abstract void delete(String label, String description, ZonedDateTime startTime, ZonedDateTime endTime);

    @Query("UPDATE clockingentity " +
            "SET " +
                "label = :replacementLabel, " +
                "description = :replacementDescription, " +
                "start_time = :replacementStartTime, " +
                "end_time = :replacementEndTime " +
            "WHERE uid IN (" +
                "SELECT uid FROM clockingentity " +
                "WHERE label = :targetLabel " +
                "AND description = :targetDescription " +
                "AND start_time = :targetStartTime " +
                "AND end_time = :targetEndTime " +
                "LIMIT 1 )")
    @TypeConverters(ZonedDateTimeConverter.class)
    abstract void replace(String targetLabel, String targetDescription, ZonedDateTime targetStartTime, ZonedDateTime targetEndTime, String replacementLabel, String replacementDescription, ZonedDateTime replacementStartTime, ZonedDateTime replacementEndTime);

    public void replace(ClockingEntity target, ClockingEntity replacement){
        replace(target.label,
                target.description,
                target.startTime,
                target.endTime,
                replacement.label,
                replacement.description,
                replacement.startTime,
                replacement.endTime);
    }

    @Query("SELECT * FROM clockingentity")
    public abstract List<ClockingEntity> getAll();
}
