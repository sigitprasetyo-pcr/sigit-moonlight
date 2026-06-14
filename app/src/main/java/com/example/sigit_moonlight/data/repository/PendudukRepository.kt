package com.example.sigit_moonlight.data.repository

import androidx.lifecycle.LiveData
import com.example.sigit_moonlight.data.database.PendudukDao
import com.example.sigit_moonlight.data.database.PendudukEntity

class PendudukRepository(private val pendudukDao: PendudukDao) {

    val allPenduduk: LiveData<List<PendudukEntity>> = pendudukDao.getAllPenduduk()
    val pendudukCount: LiveData<Int> = pendudukDao.getPendudukCount()

    fun searchPenduduk(query: String): LiveData<List<PendudukEntity>> {
        return pendudukDao.searchPenduduk("%$query%")
    }

    suspend fun insert(penduduk: PendudukEntity) {
        pendudukDao.insertPenduduk(penduduk)
    }

    suspend fun update(penduduk: PendudukEntity) {
        penduDaoUpdate(penduduk)
    }

    private suspend fun penduDaoUpdate(penduduk: PendudukEntity) {
        pendudukDao.updatePenduduk(penduduk)
    }

    suspend fun delete(penduduk: PendudukEntity) {
        pendudukDao.deletePenduduk(penduduk)
    }
}
