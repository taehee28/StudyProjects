package com.thk.floorplanviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thk.floorplanviewdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MapAdapterImpl()
        binding.mapLayout.setAdapter(adapter)
        adapter.drawIcons()
    }
}