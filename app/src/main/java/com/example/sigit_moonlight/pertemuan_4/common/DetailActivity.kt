package com.example.sigit_moonlight.pertemuan_4.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.databinding.ActivityCommonDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommonDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val title = intent.getStringExtra("EXTRA_TITLE") ?: "Detail"
        val desc = intent.getStringExtra("EXTRA_DESC") ?: ""

        binding.tvDetailTitle.text = title
        binding.tvDetailDesc.text = desc
    }

    // Handle tombol back di Toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
