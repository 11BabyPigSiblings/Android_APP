package com.goni99.smartlibrarysystem.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.goni99.smartlibrarysystem.R
import com.goni99.smartlibrarysystem.adapter.LibraryPageAdapter
import com.goni99.smartlibrarysystem.databinding.ActivityLibraryBinding

class LibraryActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityLibraryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun setViewPager() {
        with(binding) {
            libraryFrame.adapter = LibraryPageAdapter(this@LibraryActivity)

            libraryFrame.registerOnPageChangeCallback(
                object: ViewPager2.OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        bottomNavigationBar.menu.getItem(position).isChecked = true
                    }
                }
            )
            setBottomNavigation()
        }
    }

    private fun setBottomNavigation() {
        val navigation = binding.bottomNavigationBar
        navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_first -> {
                    binding.libraryFrame.currentItem = 0
                }
                R.id.menu_second -> {
                    binding.libraryFrame.currentItem = 1
                }
            }
            true
        }
    }
}