package com.example.sigit_moonlight.pertemuan_4.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sigit_moonlight.databinding.FragmentHomeBinding
import com.example.sigit_moonlight.pertemuan_10.tablayout.TabLayoutActivity
import com.example.sigit_moonlight.pertemuan_2.FormulaActivity
import com.example.sigit_moonlight.pertemuan_3.LoginActivity
import com.example.sigit_moonlight.pertemuan_4.rumus.RumusActivity
import com.example.sigit_moonlight.pertemuan_5.FifthActivity
import com.example.sigit_moonlight.pertemuan_5.WebViewActivity
import com.example.sigit_moonlight.pertemuan_9.MenuActivity
import com.example.sigit_moonlight.pertemuan_11.news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.LinearLayoutManager

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Web Desa (Portal WebView)
        binding.btnWebDesa.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("URL", "http://sigit-admin-penduduk.alwaysdata.net/dashboard")
            startActivity(intent)
        }

        // Navigasi ke Pertemuan 2
        binding.btnPertemuan2.setOnClickListener {
            startActivity(Intent(requireContext(), FormulaActivity::class.java))
        }

        // Navigasi ke Pertemuan 3
        binding.btnPertemuan3.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        // Navigasi ke Pertemuan 4
        binding.btnPertemuan4.setOnClickListener {
            startActivity(Intent(requireContext(), RumusActivity::class.java))
        }

        // Navigasi ke Pertemuan 5
        binding.btnPertemuan5.setOnClickListener {
            startActivity(Intent(requireContext(), FifthActivity::class.java))
        }

        // Navigasi ke Pertemuan 9
        binding.btnPertemuan9.setOnClickListener {
            startActivity(Intent(requireContext(), MenuActivity::class.java))
        }

        // Navigasi ke Pertemuan 10 (TabLayout & RecyclerView)
        binding.btnPertemuan10.setOnClickListener {
            startActivity(Intent(requireContext(), TabLayoutActivity::class.java))
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
        val retrofit = Retrofit.Builder()
            .baseUrl("https://berita-indo-api-next.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(NewsService::class.java)
        service.getCnnNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val posts = response.body()?.data?.posts
                    if (posts != null) {
                        newsAdapter.updateData(posts)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun logout() {
        val sharedPref = requireActivity().getSharedPreferences("BinaDesaPref", Context.MODE_PRIVATE)
        sharedPref.edit().apply {
            putBoolean("isLogin", false)
            apply()
        }

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
