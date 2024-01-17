package com.ezzy.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import com.ehsanmsz.mszprogressindicator.progressindicator.BallSpinFadeLoaderProgressIndicator
import com.ezzy.designsystem.theme.DarkBlue
import com.ezzy.presentation.R
import com.ezzy.presentation.auth.viewmodel.LoginViewModel
import com.ezzy.presentation.navigation.Screens
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavController,
    isSystemInDarkMode: Boolean = isSystemInDarkTheme()
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkMode

    val loginViewModel: LoginViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val isLoggedIn by loginViewModel.isUserLoggedIn.collectAsStateWithLifecycle(
        initialValue = false
    )


    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (useDarkIcons)
                Color.White else DarkBlue,
            darkIcons = useDarkIcons
        )
    }


    SideEffect {
        coroutineScope.launch {
            delay(3_000)
            navController.navigate(
                if (isLoggedIn) Screens.MainApp.route
                else Screens.Auth.route
            ) {
                popUpTo(Screens.Splash.route) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Pay logo",
                        modifier = Modifier.size(150.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

//                BallPulseProgressIndicator(
//                    color = MaterialTheme.colorScheme.onPrimary,
//                    modifier = Modifier.size(50.dp)
//                )

            }
        }

    }

}