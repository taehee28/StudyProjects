package com.thk.floorplanviewdemo

import android.content.Context
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView

abstract class MapAdapter<V : DeviceIcon<*>> {
    private var layout: FrameLayout? = null

    abstract fun onCreateIcon(context: Context): V
    abstract fun onBindIcon(iconView: V, position: Int)

    fun setLayout(layout: FrameLayout) {
        this.layout = layout
    }

    fun drawIcons() = layout?.also {
        val view = onCreateIcon(it.context)
        view.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)

        onBindIcon(view, it.childCount)

        Log.d("TAG", "drawIcons: count = ${it.childCount}")
        it.addView(view)
        Log.d("TAG", "drawIcons: count = ${it.childCount}")
    }
}

class MapAdapterImpl : MapAdapter<DeviceIconImpl>() {

    val list = dummyList

    override fun onCreateIcon(context: Context): DeviceIconImpl {
        return DeviceIconImpl(context)
    }

    override fun onBindIcon(iconView: DeviceIconImpl, position: Int) {
        iconView.draw(list[position])
    }
}