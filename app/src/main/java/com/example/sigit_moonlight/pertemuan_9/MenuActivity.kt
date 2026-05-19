package com.example.sigit_moonlight.pertemuan_9

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.sigit_moonlight.databinding.ActivityMenuBinding
import com.google.android.material.snackbar.Snackbar

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Data for ListView (ArrayAdapter)
        val menuItems = arrayOf(
            "Privacy Policy",
            "Terms of Service",
            "About App",
            "Contact Support",
            "F.A.Q",
            "App Version"
        )

        // Simple ArrayAdapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, menuItems)
        binding.listViewMenu.adapter = adapter

        // ListView Item Click
        binding.listViewMenu.setOnItemClickListener { _, _, position, _ ->
            val selected = menuItems[position]
            Snackbar.make(binding.root, "Melihat detail $selected...", Snackbar.LENGTH_SHORT).show()
        }
    }
}