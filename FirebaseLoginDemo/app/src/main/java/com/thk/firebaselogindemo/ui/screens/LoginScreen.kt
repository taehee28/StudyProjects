package com.thk.firebaselogindemo.ui.screens

import android.content.Context
import android.content.Intent
import android.util.Log
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
import com.thk.firebaselogindemo.ui.viewmodel.AccountViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.thk.firebaselogindemo.ui.components.LoadingDialog
import com.thk.firebaselogindemo.util.getGoogleSignInClient
import com.thk.firebaselogindemo.util.logd

@Composable
fun LoginScreen(
    viewModel: AccountViewModel = viewModel(),
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
            val googleLauncher = rememberLauncherForGoogleLogin { viewModel.loginWithGoogle(it) }

            if (state.isLoading) {
                LoadingDialog(onDismissRequest = { /*TODO*/ })
            }

            state.error?.also { showToast(it, context) }

            Button(
                onClick = {
                    googleLauncher.launch(getGoogleSignInClient(context).signInIntent)
                }
            ) {
                Text(text = "Google Login")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Guest Login")
            }

            Button(
                onClick = {
                    logd(FirebaseAuth.getInstance().currentUser.toString())
                }
            ) {
                Text(text = "Test")
            }

            Button(onClick = { Firebase.auth.signOut() }) {
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

@Composable
private fun rememberLauncherForGoogleLogin(
    onResult: (Task<GoogleSignInAccount>?) -> Unit
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult()
) { result: ActivityResult ->
    val task = result.data?.let { GoogleSignIn.getSignedInAccountFromIntent(it) }
    onResult(task)
}

private fun showToast(msg: String, context: Context) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}