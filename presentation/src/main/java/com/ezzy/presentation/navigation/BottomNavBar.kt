package com.ezzy.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ezzy.designsystem.theme.DarkBlue

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    visible: Boolean = true,
    isSystemInDarkMode: Boolean = false
) {

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier
    ) {
        Column {
            NavigationBar(
                containerColor = if (isSystemInDarkMode) DarkBlue else White,
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomNavigationItems.forEach { screen ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.icon),
                                contentDescription = null,
                                tint = if (selected) White else MaterialTheme.colorScheme.inversePrimary
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = screen.label),
                                style = MaterialTheme.typography.bodySmall,
                                color = if (selected) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.inversePrimary
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            indicatorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f),
                            selectedTextColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else Color.Black
                        )
                    )
                }
            }
        }
    }
}