package com.example.sigit_moonlight.pertemuan_10.tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sigit_moonlight.R
import com.example.sigit_moonlight.databinding.FragmentDataBinding
import com.example.sigit_moonlight.pertemuan_10.recyclerview.DesaAdapter
import com.example.sigit_moonlight.pertemuan_10.recyclerview.DesaModel

class DataFragment : Fragment() {

    private var _binding: FragmentDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listData = listOf(
            DesaModel("Sepatu Running Nike", "Rp 850.000", "", R.drawable.ic_nusadata_logo),
            DesaModel("Kemeja Flannel", "Rp 320.000", "", R.drawable.ic_nusadata_logo),
            DesaModel("Tas Ransel Laptop", "Rp 450.000", "", R.drawable.ic_nusadata_logo),
            DesaModel("Jam Tangan Casio", "Rp 1.200.000", "", R.drawable.ic_nusadata_logo),
            DesaModel("Headphone Sony", "Rp 1.500.000", "", R.drawable.ic_nusadata_logo),
            DesaModel("Kaos Polos Premium", "Rp 150.000", "", R.drawable.ic_nusadata_logo)
        )

        binding.rvDesa.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = DesaAdapter(listData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
