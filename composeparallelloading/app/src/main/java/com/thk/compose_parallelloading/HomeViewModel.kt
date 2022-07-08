package com.thk.compose_parallelloading

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.compose_parallelloading.model.AppItem
import com.thk.compose_parallelloading.model.TaskStatus
import com.thk.compose_parallelloading.ui.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel: ViewModel() {
    private val itemList = (0..20).map { i ->
        AppItem(
            id = i,
            text = "Item ${i+1}"
        )
    }

    private val _itemsState = mutableStateListOf<AppItem>()
    val itemsState: List<AppItem> = _itemsState

    init {
        _itemsState.addAll(itemList)
    }

    // ui에서 일어나는 이벤트를 sealed class를 이용해서
    // 하나의 함수로 통합
    fun uiEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ItemClick -> {
                val item = event.item
                if (item.taskStatus == TaskStatus.NOT_STARTED) {
                    _itemsState[item.id] = itemsState[item.id].copy(
                        taskStatus = TaskStatus.IN_PROGRESS
                    )

                    // 클릭이 발생한 아이템마다
                    // 코루틴 작업을 실행
                    processTask(item.id)
                }
            }
        }
    }

    private fun processTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("TAG", "processTask: ${Thread.currentThread().name}")
                backgroundTask(id)
            } finally {
                Log.d("TAG", "processTask: cancelled $id")
            }
        }
    }

    private suspend fun backgroundTask(id: Int) {
        val delay = id + Random.nextInt(5)
        Log.d("TAG", "backgroundTask: delay for id $id is $delay")
        delay(delay * 1000L)

        _itemsState[id] = itemsState[id].copy(taskStatus = TaskStatus.COMPLETED)
    }

    override fun onCleared() {
        // viewModel이 사라질 때 남아있을 수도 있는
        // 코루틴 작업을 모두 취소
        viewModelScope.cancel()
        super.onCleared()
    }
}