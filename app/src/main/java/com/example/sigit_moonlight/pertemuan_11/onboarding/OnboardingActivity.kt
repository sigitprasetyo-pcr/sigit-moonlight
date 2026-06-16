package com.example.sigit_moonlight.pertemuan_11.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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
                "Selamat Datang di NusaData",
                "Platform digital untuk mendukung pelayanan\ndan informasi desa Nusantara.",
                R.drawable.ic_onboarding_welcome
            ),
            OnboardingItem(
                "Kelola Data Desa",
                "Kelola data penduduk dan informasi desa\ndengan mudah, cepat, dan aman.",
                R.drawable.ic_onboarding_news
            ),
            OnboardingItem(
                "Aspirasi & Informasi",
                "Sampaikan aspirasi warga dan dapatkan\nberita terbaru langsung dari desamu.",
                R.drawable.ic_onboarding_start
            )
        )

        val adapter = OnboardingAdapter(onboardingItems)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        // Handle slide changes
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val isLastPage = position == onboardingItems.size - 1

                if (isLastPage) {
                    // Last slide: hide "Selanjutnya", show "Ayo Mulai!", hide "Lewati"
                    binding.btnNext.visibility = View.GONE
                    binding.btnMulai.visibility = View.VISIBLE
                    binding.btnSkip.visibility = View.GONE
                } else {
                    // Other slides: show "Selanjutnya", hide "Ayo Mulai", show "Lewati"
                    binding.btnNext.visibility = View.VISIBLE
                    binding.btnMulai.visibility = View.GONE
                    binding.btnSkip.visibility = View.VISIBLE
                }
            }
        })

        // "Selanjutnya" button
        binding.btnNext.setOnClickListener {
            val next = binding.viewPager.currentItem + 1
            if (next < onboardingItems.size) {
                binding.viewPager.currentItem = next
            }
        }

        // "Ayo Mulai!" button — only on last slide
        binding.btnMulai.setOnClickListener {
            navigateToLogin()
        }

        // "Lewati" button — skip directly to login
        binding.btnSkip.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        markOnboardingFinished()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun markOnboardingFinished() {
        val sharedPref = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
        sharedPref.edit()
            .putBoolean("onboarding_finished", true)
            .putString("onboarding_version", "v3")
            .apply()
    }
}