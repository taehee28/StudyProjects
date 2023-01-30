package com.thk.floorplanviewdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.thk.floorplanviewdemo.databinding.ItemDeviceIconBinding

abstract class DeviceIcon<T>: ConstraintLayout {
    protected val binding: ItemDeviceIconBinding = ItemDeviceIconBinding.inflate(LayoutInflater.from(context))

    constructor(context: Context) : super(context)
    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    abstract fun draw(data: T)

    override fun setActivated(activated: Boolean) {
        binding.ivBackground.isActivated = activated
    }

    override fun isActivated(): Boolean = binding.ivBackground.isActivated
}

class DeviceIconImpl(context: Context) : DeviceIcon<DeviceStatus>(context) {
    override fun draw(data: DeviceStatus) {
        tag = data.number
        x = data.x
        y = data.y
    }
}
