package com.example.sigit_moonlight

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.pertemuan_4.main.MainActivity
import com.example.sigit_moonlight.tugasp3.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            if (isLogin) {
                // Jika sudah login, langsung ke Dashboard
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Jika belum login, tampilkan halaman Login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 3000) // Tampilkan splash selama 3 detik
    }
}
