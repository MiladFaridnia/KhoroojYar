package com.faridnia.khoroojyar.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDate
import java.time.LocalTime

@Dao
interface WorkDayInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workDayInfo: WorkDayInfo): Long // Returns -1 if the insert fails due to conflict

    @Update
    suspend fun update(workDayInfo: WorkDayInfo)

    @Delete
    suspend fun delete(workDayInfo: WorkDayInfo)

    @Query("SELECT * FROM WorkDayInfos WHERE id = :id")
    suspend fun getWorkDayInfoById(id: Int): WorkDayInfo?

    @Query("SELECT * FROM WorkDayInfos WHERE day = :day LIMIT 1")
    suspend fun getWorkDayInfoByDay(day: LocalDate): WorkDayInfo?

    @Query("SELECT * FROM WorkDayInfos ORDER BY day ASC")
    suspend fun getAllWorkDayInfos(): List<WorkDayInfo>

    @Query("""
        UPDATE WorkDayInfos
        SET 
            firstEnterTime = CASE WHEN :firstEnterTime IS NOT NULL THEN :firstEnterTime ELSE firstEnterTime END,
            firstExitTime = CASE WHEN :firstExitTime IS NOT NULL THEN :firstExitTime ELSE firstExitTime END,
            secondEnterTime = CASE WHEN :secondEnterTime IS NOT NULL THEN :secondEnterTime ELSE secondEnterTime END,
            secondExitTime = CASE WHEN :secondExitTime IS NOT NULL THEN :secondExitTime ELSE secondEnterTime END
        WHERE day = :day
    """)
    suspend fun updateFieldsByDay(
        day: LocalDate,
        firstEnterTime: LocalTime?,
        firstExitTime: LocalTime?,
        secondEnterTime: LocalTime?,
        secondExitTime: LocalTime?
    )
}
