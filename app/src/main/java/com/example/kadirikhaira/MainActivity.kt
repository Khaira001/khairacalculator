package com.example.kadirikhaira

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3
            override fun createFragment(position: Int): Fragment = when (position) {
                0 -> NormalFragment()
                1 -> ScientificFragment()
                else -> StatsMatrixFragment()
            }
        }

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = when (pos) {
                0 -> "Basic"
                1 -> "Scientific"
                else -> "Stats/Matrix"
            }
        }.attach()
    }
}
