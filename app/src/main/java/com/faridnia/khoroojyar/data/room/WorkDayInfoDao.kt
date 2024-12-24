package com.faridnia.khoroojyar.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDate

@Dao
interface WorkDayInfoDao {

    @Insert
    suspend fun insert(workDayInfo: WorkDayInfo)

    @Update
    suspend fun update(workDayInfo: WorkDayInfo)

    @Delete
    suspend fun delete(workDayInfo: WorkDayInfo)

    @Query("SELECT * FROM WorkDayInfos WHERE id = :id")
    suspend fun getWorkDayInfoById(id: Int): WorkDayInfo?

    @Query("SELECT * FROM WorkDayInfos WHERE day = :day LIMIT 1") // Query by 'day'
    suspend fun getWorkDayInfoByDay(day: LocalDate): WorkDayInfo?


    @Query("SELECT * FROM WorkDayInfos ORDER BY day ASC") // Sort by 'day' in ascending order
    suspend fun getAllWorkDayInfos(): List<WorkDayInfo>
}
