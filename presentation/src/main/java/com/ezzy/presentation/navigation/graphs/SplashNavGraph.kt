package com.ezzy.presentation.navigation.graphs

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ezzy.presentation.navigation.Screens
import com.ezzy.presentation.splash.SplashScreen

fun NavGraphBuilder.splashNavGraph(navController: NavController) {
    navigation(
        startDestination = Screens.Splash.route,
        route = Screens.SplashMain.route
    ) {
        composable(route = Screens.Splash.route,
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
            SplashScreen(navController)
        }
    }
}