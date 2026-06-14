package com.example.sigit_moonlight.data.repository

import androidx.lifecycle.LiveData
import com.example.sigit_moonlight.data.database.AspirasiDao
import com.example.sigit_moonlight.data.database.AspirasiEntity

class AspirasiRepository(private val aspirasiDao: AspirasiDao) {

    val allAspirasi: LiveData<List<AspirasiEntity>> = aspirasiDao.getAllAspirasi()
    val aspirasiCount: LiveData<Int> = aspirasiDao.getAspirasiCount()

    suspend fun insert(aspirasi: AspirasiEntity) {
        aspirasiDao.insertAspirasi(aspirasi)
    }

    suspend fun update(aspirasi: AspirasiEntity) {
        aspirasiDao.updateAspirasi(aspirasi)
    }

    suspend fun delete(aspirasi: AspirasiEntity) {
        aspirasiDao.deleteAspirasi(aspirasi)
    }
}
