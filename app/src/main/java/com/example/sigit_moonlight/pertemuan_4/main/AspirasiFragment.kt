package com.example.sigit_moonlight.pertemuan_4.main

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sigit_moonlight.R
import com.example.sigit_moonlight.data.database.AspirasiEntity
import com.example.sigit_moonlight.databinding.DialogAddEditAspirasiBinding
import com.example.sigit_moonlight.databinding.DialogDetailAspirasiBinding
import com.example.sigit_moonlight.databinding.FragmentAspirasiBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*
import com.example.sigit_moonlight.notification.NotificationHelper

class AspirasiFragment : Fragment() {

    private var _binding: FragmentAspirasiBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AspirasiViewModel by viewModels()
    private lateinit var adapter: AspirasiAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAspirasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = AspirasiAdapter(
            emptyList(),
            onDetailClick = { showDetailDialog(it) },
            onEditClick = { showAddEditDialog(it) },
            onDeleteClick = { confirmDelete(it) }
        )
        binding.rvAspirasi.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAspirasi.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.allAspirasi.observe(viewLifecycleOwner) { list ->
            adapter.updateData(list)
            if (list.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.rvAspirasi.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.rvAspirasi.visibility = View.VISIBLE
            }
        }
    }

    private fun setupListeners() {
        binding.fabAddAspirasi.setOnClickListener {
            showAddEditDialog(null)
        }
    }

    private fun showAddEditDialog(aspirasi: AspirasiEntity?) {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding = DialogAddEditAspirasiBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        val isEdit = aspirasi != null
        if (isEdit) {
            dialogBinding.tvTitle.text = "Ubah Aspirasi Warga"
            dialogBinding.etJudul.setText(aspirasi!!.judulAspirasi)
            dialogBinding.etNamaPengirim.setText(aspirasi.namaPengirim)
            dialogBinding.etIsi.setText(aspirasi.isiAspirasi)
            dialogBinding.btnSaveAspirasi.text = "Perbarui Aspirasi"

            // Show status selection only when editing
            dialogBinding.layoutStatusSelection.visibility = View.VISIBLE
            when (aspirasi.status) {
                "Selesai" -> dialogBinding.rbSelesai.isChecked = true
                "Diproses" -> dialogBinding.rbDiproses.isChecked = true
                else -> dialogBinding.rbMenunggu.isChecked = true
            }
        }

        dialogBinding.btnSaveAspirasi.setOnClickListener {
            val judul = dialogBinding.etJudul.text.toString().trim()
            val pengirim = dialogBinding.etNamaPengirim.text.toString().trim()
            val isi = dialogBinding.etIsi.text.toString().trim()

            if (judul.isEmpty() || pengirim.isEmpty() || isi.isEmpty()) {
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("id-ID"))
            val tanggal = if (isEdit) aspirasi!!.tanggal else sdf.format(Date())

            val status = if (isEdit) {
                val selectedRbId = dialogBinding.rgStatus.checkedRadioButtonId
                if (selectedRbId != -1) {
                    dialogBinding.root.findViewById<RadioButton>(selectedRbId).text.toString()
                } else "Menunggu"
            } else "Menunggu"

            val newAspirasi = if (isEdit) {
                AspirasiEntity(aspirasi!!.id, judul, pengirim, isi, tanggal, status)
            } else {
                AspirasiEntity(judulAspirasi = judul, namaPengirim = pengirim, isiAspirasi = isi, tanggal = tanggal, status = status)
            }

            if (isEdit) {
                viewModel.update(newAspirasi)
                Toast.makeText(requireContext(), "Aspirasi berhasil diperbarui", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.insert(newAspirasi)
                // 🔔 Notifikasi lokal: aspirasi terkirim
                NotificationHelper.showAspirasiSentNotification(requireContext(), judul)
                Toast.makeText(requireContext(), "Aspirasi berhasil dikirim", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDetailDialog(aspirasi: AspirasiEntity) {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding = DialogDetailAspirasiBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.tvDetailJudul.text = aspirasi.judulAspirasi
        dialogBinding.tvDetailPengirim.text = "Pengirim: ${aspirasi.namaPengirim}"
        dialogBinding.tvDetailTanggal.text = aspirasi.tanggal
        dialogBinding.tvDetailIsi.text = aspirasi.isiAspirasi
        dialogBinding.tvDetailStatus.text = aspirasi.status

        // Style status chip dynamically
        val (bgColor, textColor) = when (aspirasi.status) {
            "Selesai" -> Color.parseColor("#D1FAE5") to Color.parseColor("#059669")
            "Diproses" -> Color.parseColor("#FEF3C7") to Color.parseColor("#D97706")
            else -> Color.parseColor("#F1F5F9") to Color.parseColor("#64748B") // Menunggu
        }
        dialogBinding.tvDetailStatus.backgroundTintList = ColorStateList.valueOf(bgColor)
        dialogBinding.tvDetailStatus.setTextColor(textColor)

        dialogBinding.btnCloseAspirasi.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun confirmDelete(aspirasi: AspirasiEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Aspirasi")
            .setMessage("Apakah Anda yakin ingin menghapus aspirasi ini?")
            .setPositiveButton("Hapus") { _, _ ->
                viewModel.delete(aspirasi)
                Toast.makeText(requireContext(), "Aspirasi berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
