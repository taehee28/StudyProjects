package com.thk.navigationdemo

import android.util.Log

inline fun <reified T> T.logd(msg: String) = Log.d(T::class.simpleName, msg)