package com.ezzy.presentation.navigation.graphs

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ezzy.presentation.auth.LoginScreen
import com.ezzy.presentation.navigation.Screens

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        startDestination = Screens.Login.route,
        route = Screens.AuthMain.route
    ) {
        composable(route = Screens.Login.route,
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(700),
                    initialOffsetY = { it }
                )
            },
            exitTransition = {
                slideOutVertically(
                    animationSpec = tween(700),
                    targetOffsetY = { it }
                )
            }) {
            LoginScreen(navController = navController)
        }
    }
}