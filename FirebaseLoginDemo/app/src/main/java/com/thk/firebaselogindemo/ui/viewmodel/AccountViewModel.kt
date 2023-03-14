package com.thk.firebaselogindemo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.thk.firebaselogindemo.ui.state.AccountState
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

    fun loginWithGoogle(task: Task<GoogleSignInAccount>?) {
        if (task == null) {
            _state.update {
                it.copy(
                    isLoading = false,
                    isLoggedIn = false,
                    error = "구글 로그인에 실패했습니다."
                )
            }
            return
        } else if (task.isCanceled) {
            _state.update {
                it.copy(
                    isLoading = false,
                    isLoggedIn = false,
                    error = "구글 로그인이 취소되었습니다."
                )
            }
            return
        }

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            kotlin.runCatching {
                val credential = GoogleAuthProvider.getCredential(task.result?.idToken, null)
                val result = FirebaseAuth.getInstance().signInWithCredential(credential).await()

                checkNotNull(result?.user)
            }.onFailure { throwable ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        error = throwable.localizedMessage
                    )
                }
            }.onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        error = null
                    )
                }
            }
        }
    }

    fun loginWithGuest() {

    }
}