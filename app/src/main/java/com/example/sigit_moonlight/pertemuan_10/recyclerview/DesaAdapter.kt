package com.example.sigit_moonlight.pertemuan_10.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sigit_moonlight.databinding.ItemDesaBinding

class DesaAdapter(private val listDesa: List<DesaModel>) : RecyclerView.Adapter<DesaAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemDesaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDesaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val desa = listDesa[position]
        holder.binding.apply {
            tvNamaDesa.text = desa.namaDesa
            tvHarga.text = desa.harga
            ivDesa.setImageResource(desa.gambar)
        }
    }

    override fun getItemCount(): Int = listDesa.size
}
