package com.example.sigit_moonlight.pertemuan_4.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.sigit_moonlight.databinding.FragmentProfileBinding
import com.example.sigit_moonlight.pertemuan_3.LoginActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol Keluar / Logout
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
        
        // Anda bisa menambahkan listener untuk item profil lainnya di sini jika diperlukan
        // Contoh: binding.itemEditProfile.setOnClickListener { ... }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout Aplikasi")
            .setMessage("Apakah Anda yakin ingin keluar dari akun ini?")
            .setPositiveButton("Ya, Keluar") { _, _ ->
                // Hapus sesi login
                val sharedPref = requireActivity().getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("isLogin", false)
                    apply()
                }

                // Kembali ke halaman Login
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
