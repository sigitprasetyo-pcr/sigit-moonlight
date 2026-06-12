package com.example.sigit_moonlight.pertemuan_10.tablayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.R
import com.example.sigit_moonlight.databinding.ActivityTabLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class TabLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Tab A"
                    tab.setIcon(R.drawable.ic_home)
                }
                1 -> {
                    tab.text = "Tab B"
                    tab.setIcon(R.drawable.ic_home)
                }
                2 -> {
                    tab.text = "Tab C"
                    tab.setIcon(R.drawable.ic_message) // Using ic_message as placeholder for glasses if not found
                }
            }
        }.attach()
    }
}
