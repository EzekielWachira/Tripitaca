package com.ezzy.data.domain.repository

import com.ezzy.data.domain.model.Filter
import com.ezzy.data.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {

    suspend fun saveFilters(filters: List<Filter>)

    val filters: Flow<List<Filter>>

    suspend fun applyFilter(filter: Filter, filters: List<Filter>)

    suspend fun saveUserLoggedInStatus(isUserLoggedIn: Boolean)

    suspend fun setDarkMode(isEnabled: Boolean)

    suspend fun saveUserData(userData: UserData)

    val isUserLoggedIn: Flow<Boolean>
    val isDarkModeEnabled: Flow<Boolean>
    val user: Flow<UserData?>

}