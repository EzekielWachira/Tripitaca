package com.ezzy.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ezzy.designsystem.components.CommonAppBarWithTitle
import com.ezzy.designsystem.components.CustomDivider
import com.ezzy.designsystem.theme.DarkBlue
import com.ezzy.designsystem.utils.DpDimensions
import com.ezzy.presentation.R
import com.ezzy.presentation.auth.viewmodel.LoginViewModel
import com.ezzy.presentation.home.viewmodel.HomeViewModel
import com.ezzy.presentation.navigation.Screens
import com.ezzy.presentation.profile.components.AccountDetailsSection
import com.ezzy.presentation.profile.components.LogoutConfirmationBottomSheet
import com.ezzy.presentation.profile.components.LogoutItem
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    isSystemInDarkMode: Boolean = isSystemInDarkTheme()
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkMode

    val viewModel: HomeViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val user by viewModel.firebaseUser.collectAsStateWithLifecycle(initialValue = null)

    val scrollState = rememberScrollState()
    val bottomSheetState = rememberModalBottomSheetState()
    var isLogoutSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val token = stringResource(id = R.string.web_client_id)
    val gso =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .requestProfile()
            .build()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (useDarkIcons)
                Color.White else DarkBlue,
            darkIcons = useDarkIcons
        )
    }

    Scaffold(
        topBar = {
            CommonAppBarWithTitle(
                title = "Profile",
                backIcon = R.drawable.back,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {


                AccountDetailsSection(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                    },
                    user = user
                )

                Spacer(modifier = Modifier.height(DpDimensions.Small))

                Column(
                    modifier = Modifier
                        .padding(horizontal = DpDimensions.Normal)
                ) {
                    CustomDivider(
                        text = stringResource(R.string.general),
                        modifier = Modifier.fillMaxWidth()
                    )

                    LogoutItem(
                        icon = R.drawable.logout,
                        title = stringResource(R.string.logout),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        isLogoutSheetOpen = true
                    }


                }

            }


            if (isLogoutSheetOpen) {
                LogoutConfirmationBottomSheet(
                    bottomSheetState = bottomSheetState,
                    onDismiss = { isLogoutSheetOpen = false },
                    onLogout = {
                        navController.navigate(Screens.Auth.route) {
                            popUpTo(Screens.Main.route) {
                                inclusive = false
                            }
                        }.also {
                            GoogleSignIn.getClient(context, gso)
                                .signOut()
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            loginViewModel.saveIsLoggedIn(false)
                        }
                    },
                    onCancel = {
                        isLogoutSheetOpen = false
                    }
                )
            }

        }
    }

}