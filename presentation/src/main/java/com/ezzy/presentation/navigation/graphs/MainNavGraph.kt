package com.ezzy.presentation.navigation.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ezzy.presentation.booking.BookingScreen
import com.ezzy.presentation.explore.ExploreScreen
import com.ezzy.presentation.favorite.FavoriteScreen
import com.ezzy.presentation.home.HomeScreen
import com.ezzy.presentation.inbox.InboxScreen
import com.ezzy.presentation.listing_detail.DetailScreen
import com.ezzy.presentation.listing_detail.viewmodel.DetailViewModel
import com.ezzy.presentation.navigation.Screens
import com.ezzy.presentation.profile.ProfileScreen
import com.ezzy.presentation.utils.slideInVerticallyEnterAnimation
import com.ezzy.presentation.utils.slideOutVerticallyEnterAnimation

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.mainNavGraph(
    navController: NavController, isSystemInDarkTheme: Boolean,
    viewModel: DetailViewModel
) {


    navigation(
        startDestination = Screens.Home.route,
        route = Screens.Main.route
    ) {
        composable(route = Screens.Home.route,
            enterTransition = { slideInVerticallyEnterAnimation() },
            exitTransition = { slideOutVerticallyEnterAnimation() }) {
            HomeScreen(navController = navController, detailViewModel = viewModel)
        }

        composable(route = Screens.Details.route,
            enterTransition = { slideInVerticallyEnterAnimation() },
            exitTransition = { slideOutVerticallyEnterAnimation() }) { navBackStackEntry ->
            DetailScreen(
                navController = navController,
                propertyId = navBackStackEntry.arguments?.getString("id"),
                viewModel = viewModel
            )
        }

        composable(route = Screens.Booking.route,
            enterTransition = { slideInVerticallyEnterAnimation() },
            exitTransition = { slideOutVerticallyEnterAnimation() }) { navBackStackEntry ->
            BookingScreen(
                navController = navController,
                detailViewModel = viewModel
            )
        }

        composable(route = Screens.Explore.route,
            enterTransition = { slideInVerticallyEnterAnimation() },
            exitTransition = { slideOutVerticallyEnterAnimation() }) {
            ExploreScreen(navController = navController)
        }

        composable(route = Screens.Favorites.route,
            enterTransition = { slideInVerticallyEnterAnimation() },
            exitTransition = { slideOutVerticallyEnterAnimation() }) {
            FavoriteScreen(navController = navController)
        }

        composable(route = Screens.Inbox.route,
            enterTransition = { slideInVerticallyEnterAnimation() },
            exitTransition = { slideOutVerticallyEnterAnimation() }) {
            InboxScreen(navController = navController)
        }

        composable(route = Screens.Profile.route,
            enterTransition = { slideInVerticallyEnterAnimation() },
            exitTransition = { slideOutVerticallyEnterAnimation() }) {
            ProfileScreen(navController = navController)
        }


    }

}