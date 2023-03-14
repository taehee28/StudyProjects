package com.thk.firebaselogindemo.ui.state

data class AccountState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)
