package com.example.sigit_moonlight.pertemuan_10.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sigit_moonlight.R
import com.example.sigit_moonlight.databinding.ActivityDesaListBinding

class DesaListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDesaListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDesaListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val listDesa = listOf(
            DesaModel("Desa Sukamaju", "Kecamatan Ciawi", "Pusat data pertanian dan irigasi unggul.", R.drawable.ic_nusadata_logo),
            DesaModel("Desa Bojong", "Kecamatan Kemang", "Sentra kerajinan tangan dan UMKM lokal.", R.drawable.ic_nusadata_logo),
            DesaModel("Desa Mekarsari", "Kecamatan Cileungsi", "Kawasan industri dengan pengelolaan limbah terbaik.", R.drawable.ic_nusadata_logo),
            DesaModel("Desa Ciomas", "Kecamatan Ciomas", "Wilayah penyangga dengan sistem keamanan digital.", R.drawable.ic_nusadata_logo),
            DesaModel("Desa Dramaga", "Kecamatan Dramaga", "Kawasan pendidikan dan riset teknologi desa.", R.drawable.ic_nusadata_logo)
        )

        val adapter = DesaAdapter(listDesa)
        binding.rvDesa.layoutManager = LinearLayoutManager(this)
        binding.rvDesa.adapter = adapter
    }
}
