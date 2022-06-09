package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : ComponentActivity() {
    private val TAG = MainActivity::class.simpleName

    private val myFlow = flow {
        repeat(100) {
            Log.d(TAG, ">> emit: $it")
            emit(it)
            delay(1000)
        }
    }

    private val myStateFlow = myFlow.stateIn(
        scope = lifecycleScope,
        started = SharingStarted.Lazily,
        initialValue = -1
    )

    private val myStateFlow2 = MutableStateFlow(-1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("Hello Android!")
                }
            }
        }

        Log.d(TAG, ">>>>>>>>>>>>>>>>> start coroutine")
//        flowTest()
        stateFlowTest()
//        stateFlowTest2()
        Log.d(TAG, ">>>>>>>>>>>>>>>>> end coroutine")
    }

    private fun flowTest() = CoroutineScope(Dispatchers.Main).launch {
        launch {
            myFlow.collect { Log.d(TAG, "#1st -> $it") }
        }

        delay(2000)

        launch {
            myFlow.collect { Log.d(TAG, "#2nd -> $it") }
        }
    }

    private fun stateFlowTest() = CoroutineScope(Dispatchers.Main).launch {
        launch {
            myStateFlow.collect { Log.d(TAG, "#1st -> $it") }
        }

        delay(2500)

        launch {
            myStateFlow.collect { Log.d(TAG, "#2nd -> $it") }
        }
    }

    private fun stateFlowTest2() = CoroutineScope(Dispatchers.Main).launch {
        launch {
            myFlow.collect {
                myStateFlow2.value = it
            }
        }

        delay(2000)

        launch {
            myStateFlow2.collect {
                Log.d(TAG, "stateFlowTest2: >> collect -> $it")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = name)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting(name = "Hello Android!")
    }
}