package com.example.sigit_moonlight.pertemuan_4.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.sigit_moonlight.R
import com.example.sigit_moonlight.databinding.ActivityMainBinding
import com.example.sigit_moonlight.notification.NotificationHelper
import com.example.sigit_moonlight.notification.ReminderDialogFragment
import com.example.sigit_moonlight.pertemuan_3.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Permission launcher untuk POST_NOTIFICATIONS (Android 13+)
    private val requestNotifPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val prefs = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
            val nama = prefs.getString("namaUser", "Pengguna") ?: "Pengguna"
            NotificationHelper.showWelcomeNotification(this, nama)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Buat semua notification channels
        NotificationHelper.createNotificationChannels(this)

        // 2. Minta izin notifikasi (Android 13+ / API 33)
        requestNotificationPermission()

        // 3. Tentukan fragment awal (default Home, atau sesuai deep-link dari notifikasi)
        if (savedInstanceState == null) {
            val navigateTo = intent.getStringExtra(NotificationHelper.EXTRA_NAVIGATE_TO)
            when (navigateTo) {
                NotificationHelper.NAV_PENDUDUK -> {
                    replaceFragment(PendudukFragment())
                    binding.bottomNavigation.selectedItemId = R.id.nav_penduduk
                }
                NotificationHelper.NAV_ASPIRASI -> {
                    replaceFragment(AspirasiFragment())
                    binding.bottomNavigation.selectedItemId = R.id.nav_aspirasi
                }
                else -> {
                    replaceFragment(HomeFragment())
                    binding.bottomNavigation.selectedItemId = R.id.nav_home
                }
            }
        }

        // 4. Bottom Navigation listener
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home     -> replaceFragment(HomeFragment())
                R.id.nav_penduduk -> replaceFragment(PendudukFragment())
                R.id.nav_aspirasi -> replaceFragment(AspirasiFragment())
                R.id.nav_profile  -> replaceFragment(ProfileFragment())
                else -> false
            }
            true
        }

        // 5. Tombol logout toolbar
        binding.btnLogoutToolbar.setOnClickListener {
            performLogout()
        }

        // 6. Tombol Reminder di toolbar
        binding.btnReminder.setOnClickListener {
            ReminderDialogFragment.newInstance()
                .show(supportFragmentManager, "ReminderDialog")
        }
    }

    /** Handle deep-link saat Activity sudah berjalan (onNewIntent) */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val navigateTo = intent.getStringExtra(NotificationHelper.EXTRA_NAVIGATE_TO) ?: return
        when (navigateTo) {
            NotificationHelper.NAV_PENDUDUK -> {
                replaceFragment(PendudukFragment())
                binding.bottomNavigation.selectedItemId = R.id.nav_penduduk
            }
            NotificationHelper.NAV_ASPIRASI -> {
                replaceFragment(AspirasiFragment())
                binding.bottomNavigation.selectedItemId = R.id.nav_aspirasi
            }
            else -> {
                replaceFragment(HomeFragment())
                binding.bottomNavigation.selectedItemId = R.id.nav_home
            }
        }
    }

    fun performLogout() {
        val sharedPref = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean("isLogin", false).apply()

        val intent = Intent(this, LoginActivity::class.java)
        // FIX: Gunakan CLEAR_TOP + finishAffinity() agar tidak crash di Android 14+
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finishAffinity()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                        == PackageManager.PERMISSION_GRANTED -> {
                    val prefs = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
                    val nama = prefs.getString("namaUser", "Pengguna") ?: "Pengguna"
                    NotificationHelper.showWelcomeNotification(this, nama)
                }
                else -> {
                    requestNotifPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            val prefs = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
            val nama = prefs.getString("namaUser", "Pengguna") ?: "Pengguna"
            NotificationHelper.showWelcomeNotification(this, nama)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
    }
}
