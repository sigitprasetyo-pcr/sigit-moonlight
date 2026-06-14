package com.example.sigit_moonlight.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PendudukDao {
    @Query("SELECT * FROM penduduk ORDER BY nama_lengkap ASC")
    fun getAllPenduduk(): LiveData<List<PendudukEntity>>

    @Query("SELECT * FROM penduduk WHERE nama_lengkap LIKE :query OR nik LIKE :query ORDER BY nama_lengkap ASC")
    fun searchPenduduk(query: String): LiveData<List<PendudukEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPenduduk(penduduk: PendudukEntity)

    @Update
    suspend fun updatePenduduk(penduduk: PendudukEntity)

    @Delete
    suspend fun deletePenduduk(penduduk: PendudukEntity)

    @Query("SELECT COUNT(*) FROM penduduk")
    fun getPendudukCount(): LiveData<Int>
}
