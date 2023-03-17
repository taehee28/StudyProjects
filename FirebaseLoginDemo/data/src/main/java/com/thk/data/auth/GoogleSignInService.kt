package com.thk.data.auth

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.thk.data.R
import javax.inject.Inject

interface GoogleSignInService {
    fun getSignInIntent(): Intent
    fun signOut()
}

class GoogleSignInServiceImpl @Inject constructor(
    private val context: Context
) : GoogleSignInService {

    private val client = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .requestId()
        .requestProfile()
        .build()
        .let { gso ->
            GoogleSignIn.getClient(context, gso)
        }

    override fun getSignInIntent(): Intent = client.signInIntent

    override fun signOut() {
        client.signOut()
    }
}