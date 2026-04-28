package com.example.sigit_moonlight.pertemuan_4.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.R
import com.example.sigit_moonlight.databinding.ActivityMainBinding
import com.example.sigit_moonlight.tugasp3.LoginActivity
import com.example.sigit_moonlight.pertemuan_5.WebViewActivity
import com.example.sigit_moonlight.pertemuan_4.common.DetailActivity
import com.example.sigit_moonlight.pertemuan_4.rumus.RumusActivity
import com.example.sigit_moonlight.pertemuan_2.FormulaActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. CEK LOGIN: Jika belum login, paksa ke halaman Login
        val sharedPref = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
        val isLogin = sharedPref.getBoolean("isLogin", false)
        
        if (!isLogin) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userEmail = sharedPref.getString("user_email", "Admin")
        binding.tvDashboardUser.text = "Selamat Datang, $userEmail"

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_settings -> {
                    showLogoutDialog()
                    false
                }
                else -> true
            }
        }

        // --- Tombol Fitur Lainnya ---
        binding.btnTotalWarga.setOnClickListener {
            startActivity(Intent(this, FormulaActivity::class.java))
        }

        binding.btnWebView.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("URL", "https://sigit-admin-penduduk.alwaysdata.net/dashboard")
            startActivity(intent)
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
            .setTitle("Logout Aplikasi")
            .setMessage("Apakah Anda yakin ingin keluar dari akun ini?")
            .setPositiveButton("Ya, Keluar") { _, _ ->
                // HAPUS STATUS LOGIN
                val sharedPref = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("isLogin", false)
                    apply()
                }

                // Kembali ke Login dan bersihkan history layar
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
