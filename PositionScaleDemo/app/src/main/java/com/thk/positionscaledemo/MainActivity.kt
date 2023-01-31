package com.thk.positionscaledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.thk.positionscaledemo.databinding.ActivityMainBinding

object Constants {
    // 10인치 평면도 크기에 맞는 좌표
    const val ICON_X_POSITION = 250f
    const val ICON_Y_POSITION = 185f

    // 10인치 평면도 사이즈
    const val MAP_10_WIDTH = 430
    const val MAP_10_HEIGHT = 480

    // 13인치 평면도 사이즈
    const val MAP_13_WIDTH = 346.6f
    const val MAP_13_HEIGHT = 386.6f
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showFloorPlan_13()
//        showFloorPlan_10()
    }

    /**
     * 1280x800 mdpi 화면 기준으로 좌표 보정
     */
    private fun showFloorPlan_10() {
        binding.frameLayout.setBackgroundResource(R.drawable.floorplan_10)

        // 기준이 되는 평면도 사이즈로 화면에 표시할 평면도 사이즈를 나눈다
        val xRevision = resources.getDimension(R.dimen.map_width) / Constants.MAP_10_WIDTH
        val yRevision = resources.getDimension(R.dimen.map_height) / Constants.MAP_10_HEIGHT

        val view = View(this).apply {
            layoutParams = FrameLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.icon_width), resources.getDimensionPixelSize(R.dimen.icon_height))
            setBackgroundResource(R.color.purple_700)

            // 좌표 값을 map 크기에 맞춰 보정
            x = Constants.ICON_X_POSITION * xRevision
            y = Constants.ICON_Y_POSITION * yRevision
        }

        binding.frameLayout.addView(view)
    }

    /**
     * 1920x1080 hdpi 화면 기준으로 좌표 보정
     */
    private fun showFloorPlan_13() {
        binding.frameLayout.setBackgroundResource(R.drawable.floorplan_13)

        // 기준이 되는 평면도 사이즈로 화면에 표시할 평면도 사이즈를 나눈다
        val xRevision = resources.getDimension(R.dimen.map_width) / Constants.MAP_13_WIDTH
        val yRevision = resources.getDimension(R.dimen.map_height) / Constants.MAP_13_HEIGHT

        val view = View(this).apply {
            layoutParams = FrameLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.icon_width), resources.getDimensionPixelSize(R.dimen.icon_height))
            setBackgroundResource(R.color.purple_700)

            // 좌표 값을 map 크기에 맞춰 보정
            x = Constants.ICON_X_POSITION * xRevision
            y = Constants.ICON_Y_POSITION * yRevision
        }

        binding.frameLayout.addView(view)
    }
}