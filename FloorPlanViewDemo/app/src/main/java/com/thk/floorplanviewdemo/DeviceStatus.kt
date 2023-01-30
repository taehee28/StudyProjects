package com.thk.floorplanviewdemo

data class DeviceStatus(
    val number: String,
    val switchBinary: String,
    val x: Float,
    val y: Float
)

object Constants {
    const val ON = "on"
    const val OFF = "off"
}

val dummyList = listOf(
    DeviceStatus("1", Constants.ON, 100f, 100f),
    DeviceStatus("2", Constants.OFF, 150f, 150f),
    DeviceStatus("3", Constants.ON, 200f, 200f)
)
