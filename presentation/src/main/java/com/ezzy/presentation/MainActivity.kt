package com.ezzy.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ezzy.data.data.GoogleAuthUiClient
import com.ezzy.designsystem.theme.TripitacaTheme
import com.ezzy.presentation.auth.LoginScreen
import com.ezzy.presentation.auth.viewmodel.LoginViewModel
import com.ezzy.presentation.booking.BookingScreen
import com.ezzy.presentation.listing_detail.DetailScreen
import com.ezzy.presentation.listing_detail.viewmodel.DetailViewModel
import com.ezzy.presentation.main.MainScreen
import com.ezzy.presentation.splash.SplashMain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var googleAuthUiClient: GoogleAuthUiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: DetailViewModel = hiltViewModel()
            TripitacaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    BookingScreen(rememberNavController(), detailViewModel =  viewModel)
                    SplashMain()
//                    val navController = rememberNavController()
//                    NavHost(navController = navController, startDestination = "login") {
//                        composable(route = "login") {
//                            val viewModel = viewModel<LoginViewModel>()
//                            val state by viewModel.state.collectAsStateWithLifecycle()
//
//                            LaunchedEffect(key1 = Unit) {
//                                if(googleAuthUiClient.getSignedInUser() != null) {
//                                    navController.navigate("main"){
//                                        popUpTo("login") {
//                                            inclusive = false
//                                        }
//                                    }
//                                }
//                            }
//
//                            val launcher = rememberLauncherForActivityResult(
//                                contract = ActivityResultContracts.StartIntentSenderForResult(),
//                                onResult = { result ->
//                                    if(result.resultCode == RESULT_OK) {
//                                        lifecycleScope.launch {
//                                            val signInResult = googleAuthUiClient.signInWithIntent(
//                                                intent = result.data ?: return@launch
//                                            )
//                                            viewModel.onSignInResult(signInResult)
//                                        }
//                                    }
//                                }
//                            )
//
//                            LaunchedEffect(key1 = state.isSignInSuccessful) {
//                                if(state.isSignInSuccessful) {
//                                    Toast.makeText(
//                                        applicationContext,
//                                        "Sign in successful",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//
//                                    navController.navigate("main") {
//                                        popUpTo("login") {
//                                            inclusive = false
//                                        }
//                                    }
//                                    viewModel.resetState()
//                                }
//                            }
//
//
//                            LoginScreen(
//                                state = state,
//                                onSignInClick = {
//                                    lifecycleScope.launch {
//                                        val signInIntentSender = googleAuthUiClient.signIn()
//                                        launcher.launch(
//                                            IntentSenderRequest.Builder(
//                                                signInIntentSender ?: return@launch
//                                            ).build()
//                                        )
//                                    }
//                                },
//                                navController = navController
//                            )
//
//                        }
//
//                        composable("main") {
//                            MainScreen()
//                        }
//                    }
                }
            }
        }
    }
}

