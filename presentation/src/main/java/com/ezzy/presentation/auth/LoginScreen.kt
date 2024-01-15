package com.ezzy.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ezzy.designsystem.components.CustomPadding
import com.ezzy.designsystem.components.SocialButton
import com.ezzy.designsystem.utils.DpDimensions
import com.ezzy.presentation.R
import com.ezzy.presentation.auth.state.SignInState

@Composable
fun LoginScreen(
    navController: NavController,
    isSystemInDarkMode: Boolean = isSystemInDarkTheme(),
    state: SignInState,
    onSignInClick: () -> Unit = {}
) {

    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CustomPadding(
                verticalPadding = DpDimensions.Dp20,
                horizontalPadding = DpDimensions.Normal
            ) {

                SocialButton(label = "Sign in with Google", socialIcon = R.drawable.google,
                    onClick = {
                        onSignInClick()
                    }, modifier = Modifier.fillMaxWidth()
                )

            }

        }

    }


}