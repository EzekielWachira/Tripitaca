package com.ezzy.presentation.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ezzy.presentation.listing_detail.viewmodel.DetailViewModel
import com.ezzy.presentation.navigation.BottomNavBar
import com.ezzy.presentation.navigation.Screens
import com.ezzy.presentation.navigation.Screens.Companion.MAIN
import com.ezzy.presentation.navigation.graphs.mainNavGraph

@Composable
fun MainScreen() {

    var bottomBarVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val viewModel: DetailViewModel = hiltViewModel()


    bottomBarVisible = when (navBackStackEntry?.destination?.route) {
        Screens.Home.route -> true
        Screens.Explore.route -> true
        Screens.Favorites.route -> true
        Screens.Inbox.route -> true
        Screens.Profile.route -> true
        else -> false
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                visible = bottomBarVisible,
                isSystemInDarkMode = isSystemInDarkTheme()
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.Main.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            mainNavGraph(navController = navController, isSystemInDarkTheme = false, viewModel)
        }
    }

}