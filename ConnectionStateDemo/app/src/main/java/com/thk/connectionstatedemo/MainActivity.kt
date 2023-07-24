package com.thk.connectionstatedemo

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thk.connectionstatedemo.ui.theme.ConnectionStateDemoTheme

class MainActivity : ComponentActivity() {
    private val TAG = "TAG"

    private val connectionFlow: ConnectionStateFlow by lazy {
        val manager = getSystemService(ConnectivityManager::class.java)
        ConnectionStateFlow(manager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConnectionStateDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    HomeScreen(connectionStateFlow = connectionFlow)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        connectionFlow.registerCallback()
    }

    override fun onStop() {
        connectionFlow.unregisterCallback()
        super.onStop()
    }
}

@Composable
fun HomeScreen(connectionStateFlow: ConnectionStateFlow) {
    val connectionState by connectionStateFlow.collectAsState()

    Surface {
        Box {
            Column(
                modifier = Modifier
                    .background(color = Color.LightGray)
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
            ) {

            }

            AnimatedVisibility(
                visible = !connectionState,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "네트워크 연결이 없습니다.",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .background(color = Color.Black)
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConnectionStateDemoTheme {
        /*HomeScreen()*/
    }
}