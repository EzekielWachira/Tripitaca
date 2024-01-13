package com.ezzy.presentation.explore

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.ezzy.designsystem.theme.DarkBlue
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ExploreScreen(
    navController: NavController,
    isSystemInDarkMode: Boolean = isSystemInDarkTheme()
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkMode


    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (useDarkIcons)
                Color.White else DarkBlue,
            darkIcons = useDarkIcons
        )
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Explore",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.inversePrimary
            )

        }
    }

}