package com.thk.firebaselogindemo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.thk.firebaselogindemo.ui.state.AccountState
import com.thk.firebaselogindemo.util.logd
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AccountViewModel : ViewModel() {
    private val _state = MutableStateFlow(AccountState())
    val state: StateFlow<AccountState>
        get() = _state.asStateFlow()

    fun loginWithGoogle(task: Task<GoogleSignInAccount>?) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        kotlin.runCatching {
            requireNotNull(task) { "Task is null" }

            // task에서 로그인에 사용할 계정에 대한 인스턴스를 얻기
            val account: GoogleSignInAccount = task.await()

            // 계정의 idToken으로 credential 얻기
            val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
            // 얻은 credential로 Firebase에 로그인하기
            val result: AuthResult = Firebase.auth.signInWithCredential(credential).await()

            // user 정보가 있는지 확인해서 로그인 결과 체크하기
            checkNotNull(result.user) { "user is null" }
        }.onSuccess {
            _state.update { old ->
                old.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    error = null
                )
            }
        }.onFailure {
            it.printStackTrace()

            _state.update { old ->
                old.copy(
                    isLoading = false,
                    isLoggedIn = false,
                    error = "구글 로그인에 실패하였습니다."
                )
            }
        }
    }

    fun loginWithGuest() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        kotlin.runCatching {
            val result: AuthResult = Firebase.auth.signInAnonymously().await()
            checkNotNull(result.user)
        }.onSuccess {
            _state.update { old ->
                old.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    error = null
                )
            }
        }.onFailure {
            it.printStackTrace()

            _state.update { old ->
                old.copy(
                    isLoading = false,
                    isLoggedIn = false,
                    error = "게스트 로그인에 실패하였습니다."
                )
            }
        }
    }
}