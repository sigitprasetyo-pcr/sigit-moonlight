package com.example.sigit_moonlight.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "penduduk")
data class PendudukEntity(
    @PrimaryKey
    @ColumnInfo(name = "nik")
    val nik: String,

    @ColumnInfo(name = "nama_lengkap")
    val namaLengkap: String,

    @ColumnInfo(name = "jenis_kelamin")
    val jenisKelamin: String,

    @ColumnInfo(name = "tanggal_lahir")
    val tanggalLahir: String,

    @ColumnInfo(name = "alamat")
    val alamat: String,

    @ColumnInfo(name = "rt_rw")
    val rtRw: String,

    @ColumnInfo(name = "pekerjaan")
    val pekerjaan: String
)
