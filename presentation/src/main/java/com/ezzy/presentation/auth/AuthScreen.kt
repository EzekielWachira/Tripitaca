package com.ezzy.presentation.auth

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ezzy.presentation.main.MainScreen
import com.ezzy.presentation.navigation.Screens
import com.ezzy.presentation.navigation.graphs.authNavGraph

@Composable
fun AuthScreen() {


    val navController = rememberNavController()

    Scaffold { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screens.AuthMain.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            authNavGraph(navController)

            navigation(
                route = Screens.MainApp.route,
                startDestination = Screens.Home.route
            ) {
                composable(route = Screens.Home.route,
                    enterTransition = {
                        slideInVertically(
                            animationSpec = tween(700),
                            initialOffsetY = { it }
                        )
                    },
                    exitTransition = {
                        slideOutVertically (
                            animationSpec = tween(700),
                            targetOffsetY = { it }
                        )
                    }) {
                    MainScreen()
                }
            }
        }
    }

}