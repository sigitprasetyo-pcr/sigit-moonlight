package com.example.sigit_moonlight.pertemuan_10.tablayout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DataFragment()
            1 -> DataFragment() // Reusing for demo as in image
            else -> InfoFragment()
        }
    }
}
