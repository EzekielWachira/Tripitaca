package com.ezzy.presentation.auth.state

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)