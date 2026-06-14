package com.example.sigit_moonlight.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AspirasiDao {
    @Query("SELECT * FROM aspirasi ORDER BY id DESC")
    fun getAllAspirasi(): LiveData<List<AspirasiEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAspirasi(aspirasi: AspirasiEntity)

    @Update
    suspend fun updateAspirasi(aspirasi: AspirasiEntity)

    @Delete
    suspend fun deleteAspirasi(aspirasi: AspirasiEntity)

    @Query("SELECT COUNT(*) FROM aspirasi")
    fun getAspirasiCount(): LiveData<Int>
}
