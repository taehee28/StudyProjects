package com.thk.positionscaledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.thk.positionscaledemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val xRevision = resources.getDimension(R.dimen.map_width) / 520
        val yRevision = resources.getDimension(R.dimen.map_height) / 580

        val view = View(this).apply {
            layoutParams = FrameLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.icon_width), resources.getDimensionPixelSize(R.dimen.icon_height))
            setBackgroundResource(R.color.purple_700)
            x = 315f * xRevision
            y = 220f * yRevision
        }

        binding.frameLayout.addView(view)
    }
}