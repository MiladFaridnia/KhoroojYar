package com.faridnia.khoroojyar.domain.repository

import com.faridnia.khoroojyar.data.room.WorkDayInfo
import com.faridnia.khoroojyar.data.room.WorkDayInfoDao
import java.time.LocalDate
import javax.inject.Inject

class WorkDayInfoRepository @Inject constructor(private val dao: WorkDayInfoDao) {

    suspend fun insert(workDayInfo: WorkDayInfo) {
        dao.insert(workDayInfo)
    }

    suspend fun update(workDayInfo: WorkDayInfo) {
        dao.update(workDayInfo)
    }

    suspend fun delete(workDayInfo: WorkDayInfo) {
        dao.delete(workDayInfo)
    }

    suspend fun getWorkDayInfoById(id: Int): WorkDayInfo? {
        return dao.getWorkDayInfoById(id)
    }

    suspend fun getAllWorkDayInfos(): List<WorkDayInfo> {
        return dao.getAllWorkDayInfos()
    }

    suspend fun getWorkDayInfoByDay(day: LocalDate): WorkDayInfo? {
        return dao.getWorkDayInfoByDay(day)
    }
}
