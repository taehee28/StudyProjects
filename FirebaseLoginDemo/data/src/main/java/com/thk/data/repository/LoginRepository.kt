package com.thk.data.repository

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.thk.data.auth.GoogleSignInService
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface LoginRepository {
    fun getGoogleSignInIntent(): Intent
    suspend fun loginWithGoogle(resultIntent: Intent?): Result<Unit>
    suspend fun loginWithGuest(): Result<Unit>
    fun logout()
}

class LoginRepositoryImpl @Inject constructor(
    private val googleSignInService: GoogleSignInService
) : LoginRepository {

    override fun getGoogleSignInIntent(): Intent = googleSignInService.getSignInIntent()

    override suspend fun loginWithGoogle(resultIntent: Intent?): Result<Unit> = kotlin.runCatching {
        requireNotNull(resultIntent) { "구글 로그인에 실패했습니다."}

        val task = GoogleSignIn.getSignedInAccountFromIntent(resultIntent)
        // nullable로 받으면 어떻게 되는지?
        val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
        val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
        val result: AuthResult = Firebase.auth.signInWithCredential(credential).await()

        checkNotNull(result.user) { "구글 로그인에 실패하였습니다." }
    }

    override suspend fun loginWithGuest(): Result<Unit> = kotlin.runCatching {
        val result: AuthResult = Firebase.auth.signInAnonymously().await()
        checkNotNull(result.user) { "게스트 로그인에 실패하였습니다." }
    }

    override fun logout() {
        Firebase.auth.signOut()
        googleSignInService.signOut()
    }
}