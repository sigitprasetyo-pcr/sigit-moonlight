package com.example.sigit_moonlight.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aspirasi")
data class AspirasiEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "judul_aspirasi")
    val judulAspirasi: String,

    @ColumnInfo(name = "nama_pengirim")
    val namaPengirim: String,

    @ColumnInfo(name = "isi_aspirasi")
    val isiAspirasi: String,

    @ColumnInfo(name = "tanggal")
    val tanggal: String,

    @ColumnInfo(name = "status")
    val status: String
)
