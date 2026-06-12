package com.example.sigit_moonlight.pertemuan_11.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.sigit_moonlight.R
import com.example.sigit_moonlight.databinding.ActivityOnboardingBinding
import com.example.sigit_moonlight.pertemuan_3.LoginActivity
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onboardingItems = listOf(
            OnboardingItem(
                "Selamat Datang",
                "Aplikasi Bina Desa untuk kemudahan informasi masyarakat desa.",
                R.drawable.ic_onboarding_welcome
            ),
            OnboardingItem(
                "Berita Terkini",
                "Dapatkan berita terbaru seputar desa dan sekitarnya.",
                R.drawable.ic_onboarding_news
            ),
            OnboardingItem(
                "Ayo Mulai",
                "Silahkan login untuk mengakses semua fitur kami.",
                R.drawable.ic_onboarding_start
            )
        )

        val adapter = OnboardingAdapter(onboardingItems)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == onboardingItems.size - 1) {
                    binding.btnNext.text = "Ayo Mulai"
                } else {
                    binding.btnNext.text = "Selanjutnya"
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.viewPager.currentItem < onboardingItems.size - 1) {
                binding.viewPager.currentItem += 1
            } else {
                markOnboardingFinished()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun markOnboardingFinished() {
        val sharedPref = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean("onboarding_finished", true).apply()
    }
}