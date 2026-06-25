package com.example.sigit_moonlight.pertemuan_4.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.R
import com.example.sigit_moonlight.pertemuan_4.main.MainActivity
import com.example.sigit_moonlight.pertemuan_3.LoginActivity
import com.example.sigit_moonlight.pertemuan_11.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {

    // Bump this string to force onboarding to show again after updates
    private val ONBOARDING_VERSION = "v3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            // Check if onboarding was completed with the current version
            val savedOnboardingVersion = sharedPref.getString("onboarding_version", "")
            val isOnboardingFinished = savedOnboardingVersion == ONBOARDING_VERSION

            val intent = when {
                // Already logged in → go to main app
                isLogin -> Intent(this, MainActivity::class.java)
                // Onboarding not yet done (or outdated) → show onboarding
                !isOnboardingFinished -> Intent(this, OnboardingActivity::class.java)
                // Onboarding done, not logged in → go to login
                else -> Intent(this, LoginActivity::class.java)
            }

            // FIX: Hapus FLAG_ACTIVITY_CLEAR_TASK, gunakan finishAffinity()
            // FLAG_ACTIVITY_CLEAR_TASK + finish() → crash di Android 14+ (API 34+)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finishAffinity() // Tutup semua activity di stack dengan aman
        }, 2500)
    }
}
