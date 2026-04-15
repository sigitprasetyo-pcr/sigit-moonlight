package com.example.sigit_moonlight.pertemuan_4.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.databinding.ActivityLoginBinding
import com.example.sigit_moonlight.pertemuan_4.welcome.WelcomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // LoginActivity ini menggunakan ID sesuai activity_login.xml yang terbaru
        binding.button2.setOnClickListener {
            val username = binding.editTextTextEmailAddress2.text.toString()
            val password = binding.editTextTextPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra("EXTRA_NAME", username)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Harap isi username dan password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
