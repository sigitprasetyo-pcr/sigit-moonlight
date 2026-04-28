package com.example.sigit_moonlight.tugasp3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.databinding.ActivityLoginBinding
import com.example.sigit_moonlight.pertemuan_4.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button2.setOnClickListener {
            val email = binding.editTextTextEmailAddress2.text.toString()
            val password = binding.editTextTextPassword.text.toString()

            // Simulasi Login: Pastikan email dan password tidak kosong
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // SIMPAN STATUS LOGIN KE TRUE
                val sharedPref = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("isLogin", true)
                    putString("user_email", email)
                    apply()
                }

                // Pindah ke Dashboard (MainActivity)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Tutup LoginActivity agar tidak bisa di-back
            } else {
                Toast.makeText(this, "Isi dulu email & password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
