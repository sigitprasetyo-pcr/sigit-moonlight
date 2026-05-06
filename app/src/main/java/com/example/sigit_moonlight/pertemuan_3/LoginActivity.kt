package com.example.sigit_moonlight.pertemuan_3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.databinding.ActivityLoginBinding
import com.example.sigit_moonlight.pertemuan_4.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            val sharedPref = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
            val savedUsername = sharedPref.getString("saved_username", "")
            val savedPassword = sharedPref.getString("saved_password", "")

            // Rule 1: username == password
            // Rule 2: username == saved_username && password == saved_password
            val isRule1Match = username == password && username.isNotEmpty()
            val isRule2Match = username == savedUsername && password == savedPassword && username.isNotEmpty()

            if (isRule1Match || isRule2Match) {
                with(sharedPref.edit()) {
                    putBoolean("isLogin", true)
                    apply()
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Gagal")
                    .setMessage("Username atau password salah")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
