package com.example.sigit_moonlight.pertemuan_10.recyclerview

data class DesaModel(
    val namaDesa: String,
    val harga: String,
    val deskripsi: String,
    val gambar: Int
) {
    // Constructor untuk mendukung pemanggilan 3 parameter di Catalog (DataFragment)
    constructor(nama: String, harga: String, gambar: Int) : this(nama, harga, "", gambar)
}
