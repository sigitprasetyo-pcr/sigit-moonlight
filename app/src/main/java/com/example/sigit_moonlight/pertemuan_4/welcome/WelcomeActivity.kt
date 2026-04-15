package com.example.sigit_moonlight.pertemuan_4.welcome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.databinding.ActivityWelcomeBinding
import com.example.sigit_moonlight.pertemuan_4.main.MainActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("EXTRA_NAME") ?: "User"
        binding.tvWelcomeName.text = name

        binding.root.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("USERNAME", name)
            startActivity(intent)
            finish()
        }, 2000)
    }
}
