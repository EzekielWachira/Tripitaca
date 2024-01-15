package com.ezzy.data.domain.repository

import com.ezzy.data.domain.model.Filter
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {

    suspend fun saveFilters(filters: List<Filter>)

    val filters: Flow<List<Filter>>

    suspend fun applyFilter(filter: Filter, filters: List<Filter>)

    suspend fun saveUserLoggedInStatus(isUserLoggedIn: Boolean)

    suspend fun setDarkMode(isEnabled: Boolean)

    val isUserLoggedIn: Flow<Boolean>
    val isDarkModeEnabled: Flow<Boolean>

}