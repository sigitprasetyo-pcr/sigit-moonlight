package com.example.sigit_moonlight.pertemuan_4.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sigit_moonlight.databinding.FragmentListBinding
import com.example.sigit_moonlight.pertemuan_9.MenuActivity
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle Search Button
        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            if (query.isNotEmpty()) {
                Snackbar.make(binding.root, "Mencari data: $query", Snackbar.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Masukkan kata kunci!", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Chip Selection
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chip = group.findViewById<Chip>(checkedIds[0])
                chip?.let {
                    Snackbar.make(binding.root, "Kategori: ${it.text}", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        // Handle "Menu Selengkapnya" Button (ListView)
        binding.btnMenuSelengkapnya.setOnClickListener {
            val intent = Intent(requireContext(), MenuActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}