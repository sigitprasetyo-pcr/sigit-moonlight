package com.example.sigit_moonlight.pertemuan_4.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sigit_moonlight.R
import com.example.sigit_moonlight.databinding.FragmentHomeBinding
import com.example.sigit_moonlight.pertemuan_11.news.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    
    private val pendudukViewModel: PendudukViewModel by viewModels()
    private val aspirasiViewModel: AspirasiViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe Counts directly from Room Database
        pendudukViewModel.pendudukCount.observe(viewLifecycleOwner) { count ->
            binding.tvStatPendudukCount.text = (count ?: 0).toString()
        }

        aspirasiViewModel.aspirasiCount.observe(viewLifecycleOwner) { count ->
            binding.tvStatAspirasiCount.text = (count ?: 0).toString()
        }

        // Setup Main Menu Clicks
        binding.btnMenuPenduduk.setOnClickListener {
            (activity as? MainActivity)?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.selectedItemId = R.id.nav_penduduk
        }

        binding.btnMenuAspirasi.setOnClickListener {
            (activity as? MainActivity)?.findViewById<BottomNavigationView>(R.id.bottomNavigation)?.selectedItemId = R.id.nav_aspirasi
        }

        binding.btnMenuBerita.setOnClickListener {
            binding.scrollView.smoothScrollTo(0, binding.tvBeritaHeader.top)
        }

        binding.btnMenuProfilDesa.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Profil Desa NusaData")
                .setMessage("Desa NusaData merupakan kawasan percontohan program Bina Desa Digital.\n\nSistem ini dirancang untuk memudahkan administrasi data penduduk secara mandiri dan menampung aspirasi warga demi pembangunan desa yang lebih transparan dan inklusif.")
                .setPositiveButton("Tutup", null)
                .show()
        }

        // Tombol Keluar Akun
        binding.btnExit.setOnClickListener {
            logout()
        }

        setupRecyclerView()
        fetchNews()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(emptyList())
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = newsAdapter
    }

    private fun fetchNews() {
        binding.pbNews.visibility = View.VISIBLE
        val retrofit = Retrofit.Builder()
            .baseUrl("https://berita-indo-api.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(NewsService::class.java)
        service.getCnnNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                binding.pbNews.visibility = View.GONE
                if (response.isSuccessful) {
                    val posts = response.body()?.data
                    if (posts != null) {
                        newsAdapter.updateData(posts)
                        binding.tvStatNewsCount.text = posts.size.toString()
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                binding.pbNews.visibility = View.GONE
                // Handle failure
            }
        })
    }

    private fun logout() {
        // Delegasikan ke MainActivity agar pakai finishAffinity() - aman di Android 14+
        (activity as? MainActivity)?.performLogout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
