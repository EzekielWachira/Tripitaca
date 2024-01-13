package com.ezzy.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ezzy.presentation.R

sealed class Screens(val route: String, @StringRes val label: Int, @DrawableRes val icon: Int) {

    object Home: Screens(HOME, R.string.home, R.drawable.home)
    object Explore: Screens(EXPLORE, R.string.explore, R.drawable.search)
    object Favorites: Screens(FAVORITES, R.string.favorites, R.drawable.favorite)
    object Inbox: Screens(INBOX, R.string.inbox, R.drawable.messages)
    object Profile: Screens(PROFILE, R.string.profile, R.drawable.profile)
    object Main: Screens(MAIN, R.string.main, R.drawable.profile)

    companion object {
        const val HOME = "home"
        const val EXPLORE = "explore"
        const val FAVORITES = "favorites"
        const val INBOX = "inbox"
        const val PROFILE = "profile"
        const val MAIN = "main"
    }
}


val bottomNavigationItems = listOf(
    Screens.Home,
    Screens.Explore,
    Screens.Favorites,
    Screens.Inbox,
    Screens.Profile
)