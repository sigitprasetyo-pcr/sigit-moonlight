package com.example.sigit_moonlight.pertemuan_2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.databinding.ActivityFormulaBinding

class FormulaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormulaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormulaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar sebagai Action Bar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Ambil data dari intent (Judul & Deskripsi dari Halaman Utama)
        val title = intent.getStringExtra("title") ?: "Rumus"
        val desc = intent.getStringExtra("description") ?: "-"
        
        binding.tvTitleFormula.text = title
        binding.tvDescFormula.text = desc

        // 1. Rumus Bangun Datar: Luas Segitiga (0.5 * alas * tinggi)
        binding.btnHitungSegitiga.setOnClickListener {
            val alasStr = binding.etAlas.text.toString()
            val tinggiStr = binding.etTinggi.text.toString()

            if (alasStr.isNotEmpty() && tinggiStr.isNotEmpty()) {
                val alas = alasStr.toDouble()
                val tinggi = tinggiStr.toDouble()
                val hasil = 0.5 * alas * tinggi
                binding.tvHasilSegitiga.text = "Hasil Luas: $hasil"
                Log.e("Hitung", "Luas Segitiga: $hasil")
            } else {
                Toast.makeText(this, "Masukkan alas dan tinggi", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. Rumus Bangun Ruang: Volume Kubus (sisi^3)
        binding.btnHitungKubus.setOnClickListener {
            val sisiStr = binding.etSisi.text.toString()

            if (sisiStr.isNotEmpty()) {
                val sisi = sisiStr.toDouble()
                val hasil = sisi * sisi * sisi
                binding.tvHasilKubus.text = "Hasil Volume: $hasil"
                Log.e("Hitung", "Volume Kubus: $hasil")
            } else {
                Toast.makeText(this, "Masukkan panjang sisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Handle tombol back di Toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("Lifecycle", "FormulaActivity: onDestroy")
    }
}
