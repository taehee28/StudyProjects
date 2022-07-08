package com.thk.compose_parallelloading.ui

import com.thk.compose_parallelloading.model.AppItem

sealed class UiEvent {
    data class ItemClick(val item: AppItem): UiEvent()
}
