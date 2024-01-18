package com.ezzy.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ezzy.presentation.R

sealed class Screens(val route: String, @StringRes val label: Int, @DrawableRes val icon: Int) {

    object Home : Screens(HOME, R.string.home, R.drawable.home)
    object Explore : Screens(EXPLORE, R.string.explore, R.drawable.search)
    object Favorites : Screens(FAVORITES, R.string.favorites, R.drawable.favorite)
    object Inbox : Screens(INBOX, R.string.inbox, R.drawable.messages)
    object Profile : Screens(PROFILE, R.string.profile, R.drawable.profile)
    object Main : Screens(MAIN, R.string.main, R.drawable.profile)
    object Details : Screens(DETAILS, R.string.details, R.drawable.placeholder_ic)
    object Booking : Screens(BOOKING, R.string.booking, R.drawable.placeholder_ic)
    object Splash : Screens(SPLASH, R.string.splash, R.drawable.placeholder_ic)
    object SplashMain : Screens(SPLASH_MAIN, R.string.splash_main, R.drawable.placeholder_ic)
    object MainApp : Screens(MAIN_APP, R.string.splash_main, R.drawable.placeholder_ic)
    object Login : Screens(LOGIN, R.string.login, R.drawable.placeholder_ic)
    object AuthMain : Screens(AUTH_MAIN, R.string.auth, R.drawable.placeholder_ic)
    object Auth : Screens(AUTH, R.string.auth, R.drawable.placeholder_ic)
    object Authentication : Screens(AUTHENTICATION, R.string.auth, R.drawable.placeholder_ic)
    object ViewAll : Screens(VIEW_ALL, R.string.view_all, R.drawable.placeholder_ic)

    companion object {
        const val HOME = "home"
        const val EXPLORE = "explore"
        const val FAVORITES = "favorites"
        const val INBOX = "inbox"
        const val PROFILE = "profile"
        const val MAIN = "main"
        const val DETAILS = "details/{id}"
        const val BOOKING = "booking/{id}"
        const val SPLASH = "splash"
        const val SPLASH_MAIN = "splash_main"
        const val LOGIN = "login"
        const val MAIN_APP = "main_app"
        const val AUTH_MAIN = "auth_app"
        const val AUTH = "auth"
        const val AUTHENTICATION = "authentication"
        const val VIEW_ALL = "view_all"
    }
}


val bottomNavigationItems = listOf(
    Screens.Home,
    Screens.Explore,
    Screens.Favorites,
    Screens.Inbox,
    Screens.Profile
)