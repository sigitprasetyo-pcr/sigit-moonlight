package com.example.sigit_moonlight.pertemuan_4.main

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sigit_moonlight.data.database.AspirasiEntity
import com.example.sigit_moonlight.databinding.ItemAspirasiBinding

class AspirasiAdapter(
    private var list: List<AspirasiEntity>,
    private val onDetailClick: (AspirasiEntity) -> Unit,
    private val onEditClick: (AspirasiEntity) -> Unit,
    private val onDeleteClick: (AspirasiEntity) -> Unit
) : RecyclerView.Adapter<AspirasiAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAspirasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AspirasiEntity) {
            binding.tvJudul.text = item.judulAspirasi
            binding.tvPengirim.text = "Oleh: ${item.namaPengirim}"
            binding.tvTanggal.text = item.tanggal
            binding.tvIsiSnippet.text = item.isiAspirasi
            binding.tvStatus.text = item.status

            // Style status chip dynamically
            val (bgColor, textColor) = when (item.status) {
                "Selesai" -> Color.parseColor("#D1FAE5") to Color.parseColor("#059669")
                "Diproses" -> Color.parseColor("#FEF3C7") to Color.parseColor("#D97706")
                else -> Color.parseColor("#F1F5F9") to Color.parseColor("#64748B") // Menunggu
            }
            binding.tvStatus.backgroundTintList = ColorStateList.valueOf(bgColor)
            binding.tvStatus.setTextColor(textColor)
            binding.leftAccent.setBackgroundColor(textColor)

            binding.btnDetailAspirasi.setOnClickListener { onDetailClick(item) }
            binding.btnEditAspirasi.setOnClickListener { onEditClick(item) }
            binding.btnDeleteAspirasi.setOnClickListener { onDeleteClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAspirasiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<AspirasiEntity>) {
        list = newList
        notifyDataSetChanged()
    }
}
