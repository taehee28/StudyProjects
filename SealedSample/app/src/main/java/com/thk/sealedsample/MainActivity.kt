package com.thk.sealedsample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thk.data.model.Post
import com.thk.sealedsample.ui.theme.SealedSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = MainActivity::class.simpleName

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SealedSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    StateView(uiStateFlow = viewModel.uiState)
                }
            }
        }
    }
}

@Composable
fun StateView(uiStateFlow: StateFlow<UiState<List<Post>>>) {
    val uiState by uiStateFlow.collectAsState()

    Box(
        contentAlignment = Alignment.Center
    ) {
        when (val state = uiState) {
            is UiState.Init -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(100.dp)
                        .heightIn(100.dp),
                    strokeWidth = 5.dp
                )
            }
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.data) {
                        Text(
                            text = it.toString(),
                            modifier = Modifier.padding(8.dp)
                        )
                        Divider()
                    }
                }
            }
            is UiState.Error -> {
                Text(
                    text = state.message ?: "null"
                )
            }
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SealedSampleTheme {
        Greeting("Android")
    }
}