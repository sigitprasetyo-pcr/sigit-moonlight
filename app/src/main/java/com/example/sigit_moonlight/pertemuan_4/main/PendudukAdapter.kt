package com.example.sigit_moonlight.pertemuan_4.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sigit_moonlight.data.database.PendudukEntity
import com.example.sigit_moonlight.databinding.ItemPendudukBinding

class PendudukAdapter(
    private var list: List<PendudukEntity>,
    private val onDetailClick: (PendudukEntity) -> Unit,
    private val onEditClick: (PendudukEntity) -> Unit,
    private val onDeleteClick: (PendudukEntity) -> Unit
) : RecyclerView.Adapter<PendudukAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPendudukBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PendudukEntity) {
            binding.tvName.text = item.namaLengkap
            binding.tvNik.text = item.nik
            binding.tvAlamat.text = "${item.alamat}, RT/RW ${item.rtRw}"

            binding.btnDetail.setOnClickListener { onDetailClick(item) }
            binding.btnEdit.setOnClickListener { onEditClick(item) }
            binding.btnDelete.setOnClickListener { onDeleteClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPendudukBinding.inflate(
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

    fun updateData(newList: List<PendudukEntity>) {
        list = newList
        notifyDataSetChanged()
    }
}
