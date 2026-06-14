package com.example.sigit_moonlight.pertemuan_4.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sigit_moonlight.R
import com.example.sigit_moonlight.data.database.PendudukEntity
import com.example.sigit_moonlight.databinding.DialogAddEditPendudukBinding
import com.example.sigit_moonlight.databinding.DialogDetailPendudukBinding
import com.example.sigit_moonlight.databinding.FragmentPendudukBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class PendudukFragment : Fragment() {

    private var _binding: FragmentPendudukBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PendudukViewModel by viewModels()
    private lateinit var adapter: PendudukAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendudukBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = PendudukAdapter(
            emptyList(),
            onDetailClick = { showDetailDialog(it) },
            onEditClick = { showAddEditDialog(it) },
            onDeleteClick = { confirmDelete(it) }
        )
        binding.rvPenduduk.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPenduduk.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.allPenduduk.observe(viewLifecycleOwner) { list ->
            adapter.updateData(list)
            if (list.isEmpty()) {
                binding.emptyState.visibility = View.VISIBLE
                binding.rvPenduduk.visibility = View.GONE
            } else {
                binding.emptyState.visibility = View.GONE
                binding.rvPenduduk.visibility = View.VISIBLE
            }
        }
    }

    private fun setupListeners() {
        binding.etSearch.doAfterTextChanged {
            viewModel.setSearchQuery(it.toString())
        }

        binding.fabAddPenduduk.setOnClickListener {
            showAddEditDialog(null)
        }
    }

    private fun showAddEditDialog(penduduk: PendudukEntity?) {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding = DialogAddEditPendudukBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        val isEdit = penduduk != null
        if (isEdit) {
            dialogBinding.tvTitle.text = "Ubah Data Penduduk"
            dialogBinding.etNik.setText(penduduk!!.nik)
            dialogBinding.etNik.isEnabled = false // Primary key cannot be changed
            dialogBinding.etNamaLengkap.setText(penduduk.namaLengkap)
            if (penduduk.jenisKelamin == "Laki-laki") {
                dialogBinding.rbLaki.isChecked = true
            } else {
                dialogBinding.rbPerempuan.isChecked = true
            }
            dialogBinding.etTanggalLahir.setText(penduduk.tanggalLahir)
            dialogBinding.etAlamat.setText(penduduk.alamat)
            dialogBinding.etRtRw.setText(penduduk.rtRw)
            dialogBinding.etPekerjaan.setText(penduduk.pekerjaan)
        }

        // Birth Date Picker Dialog
        dialogBinding.etTanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
                    dialogBinding.etTanggalLahir.setText(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        dialogBinding.btnSave.setOnClickListener {
            val nik = dialogBinding.etNik.text.toString().trim()
            val nama = dialogBinding.etNamaLengkap.text.toString().trim()
            val tglLahir = dialogBinding.etTanggalLahir.text.toString().trim()
            val alamat = dialogBinding.etAlamat.text.toString().trim()
            val rtRw = dialogBinding.etRtRw.text.toString().trim()
            val pekerjaan = dialogBinding.etPekerjaan.text.toString().trim()

            val selectedRbId = dialogBinding.rgJenisKelamin.checkedRadioButtonId
            val jk = if (selectedRbId != -1) {
                dialogBinding.root.findViewById<RadioButton>(selectedRbId).text.toString()
            } else ""

            if (nik.length < 16) {
                Toast.makeText(requireContext(), "NIK harus 16 digit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (nama.isEmpty() || jk.isEmpty() || tglLahir.isEmpty() || alamat.isEmpty() || rtRw.isEmpty() || pekerjaan.isEmpty()) {
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newPenduduk = PendudukEntity(nik, nama, jk, tglLahir, alamat, rtRw, pekerjaan)
            if (isEdit) {
                viewModel.update(newPenduduk)
                Toast.makeText(requireContext(), "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.insert(newPenduduk)
                Toast.makeText(requireContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDetailDialog(penduduk: PendudukEntity) {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding = DialogDetailPendudukBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.tvDetailNik.text = penduduk.nik
        dialogBinding.tvDetailNama.text = penduduk.namaLengkap
        dialogBinding.tvDetailJenisKelamin.text = penduduk.jenisKelamin
        dialogBinding.tvDetailTanggalLahir.text = penduduk.tanggalLahir
        dialogBinding.tvDetailAlamat.text = penduduk.alamat
        dialogBinding.tvDetailRtRw.text = penduduk.rtRw
        dialogBinding.tvDetailPekerjaan.text = penduduk.pekerjaan

        dialogBinding.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun confirmDelete(penduduk: PendudukEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Data Penduduk")
            .setMessage("Apakah Anda yakin ingin menghapus data ${penduduk.namaLengkap}?")
            .setPositiveButton("Hapus") { _, _ ->
                viewModel.delete(penduduk)
                Toast.makeText(requireContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
