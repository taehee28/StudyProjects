package com.thk.firebaselogindemo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen() {
    // TODO: 로그인 화면 구현
    Scaffold {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Google Login")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Guest Login")
            }
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        LoginScreen()
    }
}