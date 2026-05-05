package com.example.sigit_moonlight.pertemuan_4.main

import android.os.Bundle
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sigit_moonlight.R
import com.example.sigit_moonlight.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) replaceFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_about -> replaceFragment(AboutFragment())
                R.id.nav_profile -> replaceFragment(ProfileFragment())
                else -> false
            }
            true
        }

        binding.btnLogoutToolbar.setOnClickListener {
            val sharedPref = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
            sharedPref.edit().putBoolean("isLogin", false).apply()

            val intent = Intent(this, com.example.sigit_moonlight.pertemuan_3.LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
    }
}
