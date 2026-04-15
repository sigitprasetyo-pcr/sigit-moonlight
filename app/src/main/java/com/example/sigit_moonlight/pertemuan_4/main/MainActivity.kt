package com.example.sigit_moonlight.pertemuan_4.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.databinding.ActivityMainBinding
import com.example.sigit_moonlight.tugasp3.LoginActivity
import com.example.sigit_moonlight.pertemuan_5.FifthActivity
import com.example.sigit_moonlight.pertemuan_4.common.DetailActivity
import com.example.sigit_moonlight.pertemuan_4.rumus.RumusActivity
import com.example.sigit_moonlight.pertemuan_2.FormulaActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("USERNAME") ?: "User"
        binding.tvDashboardUser.text = "Halo, $username!"

        // Button untuk Pertemuan 2 (Formula)
        binding.btnRumus.setOnClickListener {
            val intent = Intent(this, FormulaActivity::class.java)
            intent.putExtra("title", "Rumus Bangun")
            intent.putExtra("description", "Perhitungan Luas Segitiga dan Volume Kubus.")
            startActivity(intent)
        }

        // Button untuk Pertemuan 4 (Rumus detail)
        binding.btnCustom1.setOnClickListener {
            val intent = Intent(this, RumusActivity::class.java)
            intent.putExtra("EXTRA_TITLE", "Rumus Bangun")
            startActivity(intent)
        }

        binding.btnCustom2.setOnClickListener {
            moveToPage("Detail Info", "Ini adalah halaman detail aplikasi Sigit Moonlight.")
        }

        binding.btnPertemuan5.setOnClickListener {
            val intent = Intent(this, FifthActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun moveToPage(title: String, desc: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("EXTRA_TITLE", title)
        intent.putExtra("EXTRA_DESC", desc)
        startActivity(intent)
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .setNegativeButton("Tidak") { _, _ ->
                Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }
}
