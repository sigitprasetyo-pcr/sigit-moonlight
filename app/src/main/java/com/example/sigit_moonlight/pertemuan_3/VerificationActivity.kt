package com.example.sigit_moonlight.pertemuan_3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.databinding.ActivityVerificationBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class VerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("NAME") ?: ""
        val phone = intent.getStringExtra("PHONE") ?: ""
        val username = intent.getStringExtra("USERNAME") ?: ""
        val password = intent.getStringExtra("PASSWORD") ?: ""

        binding.btnVerify.setOnClickListener {
            val otpInput = binding.etOtp.text.toString()

            if (otpInput == phone && otpInput.isNotEmpty()) {
                // Save to SharedPreferences
                val sharedPref = getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("saved_username", username)
                    putString("saved_password", password)
                    putString("saved_name", name)
                    putString("saved_phone", phone)
                    apply()
                }

                // Redirect to Login or Main
                MaterialAlertDialogBuilder(this)
                    .setTitle("Berhasil")
                    .setMessage("Registrasi berhasil, silakan login")
                    .setPositiveButton("OK") { _, _ ->
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    .show()
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Error")
                    .setMessage("Kode OTP salah atau kosong!")
                    .setPositiveButton("Coba Lagi", null)
                    .show()
            }
        }
    }
}
