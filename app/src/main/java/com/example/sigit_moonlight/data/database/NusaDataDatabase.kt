package com.example.sigit_moonlight.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PendudukEntity::class, AspirasiEntity::class], version = 1, exportSchema = false)
abstract class NusaDataDatabase : RoomDatabase() {

    abstract fun pendudukDao(): PendudukDao
    abstract fun aspirasiDao(): AspirasiDao

    companion object {
        @Volatile
        private var INSTANCE: NusaDataDatabase? = null

        fun getDatabase(context: Context): NusaDataDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NusaDataDatabase::class.java,
                    "nusadata_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
