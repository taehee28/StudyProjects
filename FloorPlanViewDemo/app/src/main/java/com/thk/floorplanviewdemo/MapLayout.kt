package com.thk.floorplanviewdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout

class MapLayout : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_map, this, false)
    }

    fun setAdapter(adapter: MapAdapter<*>) {
        adapter.setLayout(this)
    }
}