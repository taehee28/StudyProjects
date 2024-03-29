package com.thk.firebaselogindemo.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.thk.firebaselogindemo.ui.viewmodel.LoginViewModel
import com.thk.firebaselogindemo.ui.components.LoadingDialog

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val state by viewModel.state.collectAsState()

            val context = LocalContext.current
            val googleLauncher = rememberLauncherForGoogleLogin { intent ->
                viewModel.loginWithGoogle(intent)
            }

            if (state.isLoading) {
                LoadingDialog(onDismissRequest = { /*TODO*/ })
            }

            // FIXME: 한번 토스트가 뜬 이후로는 같은 내용의 토스트가 표시되지 않음
            state.error?.also { showToast(it, context) }

            Button(
                onClick = {
                    googleLauncher.launch(viewModel.googleSignInIntent)
                }
            ) {
                Text(text = "Google Login")
            }
            Button(onClick = { viewModel.loginWithGuest() }) {
                Text(text = "Guest Login")
            }

            Button(
                onClick = {
                    /*showToast("${FirebaseAuth.getInstance().currentUser != null}", context)*/
                }
            ) {
                Text(text = "Test")
            }

            Button(
                onClick = {
                    viewModel.logout()
                }
            ) {
                Text(text = "Logout")
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

/**
 * 구글 계정 선택 창 띄우는 launcher
 */
@Composable
private fun rememberLauncherForGoogleLogin(
    onResult: (Intent?) -> Unit
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult()
) { result: ActivityResult ->
    onResult(result.data)
}

private fun showToast(msg: String, context: Context) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}