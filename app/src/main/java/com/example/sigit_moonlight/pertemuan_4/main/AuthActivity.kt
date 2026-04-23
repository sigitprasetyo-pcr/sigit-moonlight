package com.example.sigit_moonlight.pertemuan_4.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsernameAuth.text.toString()
            val password = binding.etPasswordAuth.text.toString()

            if (username == password && username.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Gagal")
                    .setMessage("Silahkan coba lagi")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }
}