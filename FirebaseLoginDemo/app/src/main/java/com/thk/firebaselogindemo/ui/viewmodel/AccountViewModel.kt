package com.thk.firebaselogindemo.ui.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thk.data.repository.LoginRepository
import com.thk.firebaselogindemo.ui.state.AccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AccountState())
    val state: StateFlow<AccountState>
        get() = _state.asStateFlow()

    val googleSignInIntent: Intent
        get() = loginRepository.getGoogleSignInIntent()

    fun loginWithGoogle(resultIntent: Intent?) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        loginRepository
            .loginWithGoogle(resultIntent = resultIntent)
            .onSuccess {
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
                        error = it.message
                    )
                }
            }
    }

    fun loginWithGuest() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        loginRepository
            .loginWithGuest()
            .onSuccess {
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

    fun logout() {
        loginRepository.logout()
    }
}