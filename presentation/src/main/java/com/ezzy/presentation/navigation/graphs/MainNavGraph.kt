package com.ezzy.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ezzy.presentation.explore.ExploreScreen
import com.ezzy.presentation.favorite.FavoriteScreen
import com.ezzy.presentation.home.HomeScreen
import com.ezzy.presentation.inbox.InboxScreen
import com.ezzy.presentation.navigation.Screens
import com.ezzy.presentation.profile.ProfileScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController, isSystemInDarkTheme: Boolean) {

    navigation(
        startDestination = Screens.Home.route,
        route = Screens.Main.route
    ) {
        composable(route = Screens.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screens.Explore.route) {
            ExploreScreen(navController = navController)
        }

        composable(route = Screens.Favorites.route) {
            FavoriteScreen(navController = navController)
        }

        composable(route = Screens.Inbox.route) {
            InboxScreen(navController = navController)
        }

        composable(route = Screens.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }

}