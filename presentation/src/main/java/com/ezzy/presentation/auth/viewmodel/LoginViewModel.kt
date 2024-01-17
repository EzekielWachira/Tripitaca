package com.ezzy.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezzy.data.domain.model.SignInResult
import com.ezzy.data.domain.model.UserData
import com.ezzy.data.domain.repository.PreferenceRepository
import com.ezzy.presentation.auth.state.SignInState
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    val isUserLoggedIn = preferenceRepository.isUserLoggedIn

    fun saveAuthUser(user: FirebaseUser) {
        viewModelScope.launch {
            val userData = UserData(
                username = user.displayName,
                profilePictureUrl = user.photoUrl.toString(),
                userId = user.uid
            )

            preferenceRepository.saveUserData(userData)
        }

    }

    fun saveIsLoggedIn(isLoggedIn: Boolean) {
        viewModelScope.launch {
            preferenceRepository.saveUserLoggedInStatus(isLoggedIn)
        }
    }

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

}