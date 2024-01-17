package com.ezzy.data.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ezzy.data.data.PreferenceRepositoryImpl.PreferenceKeys.amenitiesFilters
import com.ezzy.data.domain.model.Filter
import com.ezzy.data.domain.model.UserData
import com.ezzy.data.domain.repository.PreferenceRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class PreferenceRepositoryImpl(private val context: Context, private val gson: Gson) :
    PreferenceRepository {

    private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(name = "tripitaca")

    private object PreferenceKeys {
        val isUserLoggedIn = booleanPreferencesKey("is_user_logged_in")
        val isDarkModeEnabled = booleanPreferencesKey("is_dark_mode_enabled")
        val amenitiesFilters = stringPreferencesKey("filters")
        val user = stringPreferencesKey("user_data")
    }

    override suspend fun saveFilters(filters: List<Filter>) {
        context.dataStore.edit { preference ->
            preference[amenitiesFilters] = Gson().toJson(filters)
        }
    }

    override val filters: Flow<List<Filter>>
        get() = context.dataStore.data
            .catch { e ->
                if (e is IOException) emit(emptyPreferences())
                else throw e
            }.map { preferences ->
                val listType = object : TypeToken<List<Filter>>() {}.type
                Gson().fromJson(preferences[amenitiesFilters], listType) ?: emptyList()
            }

    override suspend fun applyFilter(filter: Filter, filters: List<Filter>) {
        val updatedFilters = filters.map {
            if (it == filter) {
                it.copy(isSelected = filter.isSelected)
            } else {
                it
            }
        }

        context.dataStore.edit { preference ->
            preference[amenitiesFilters] = Gson().toJson(updatedFilters)
        }
    }


    override suspend fun saveUserLoggedInStatus(isUserLoggedIn: Boolean) {
        context.dataStore.edit { preference ->
            preference[PreferenceKeys.isUserLoggedIn] = isUserLoggedIn
        }
    }

    override suspend fun saveUserData(userData: UserData) {
        context.dataStore.edit { preference ->
            preference[PreferenceKeys.user] = gson.toJson(userData)
        }
    }

    override suspend fun setDarkMode(isEnabled: Boolean) {
        context.dataStore.edit { preference ->
            preference[PreferenceKeys.isDarkModeEnabled] = isEnabled
        }
    }

    override val isUserLoggedIn: Flow<Boolean>
        get() = context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }.map { preferences ->
                preferences[PreferenceKeys.isUserLoggedIn] ?: false
            }
    override val isDarkModeEnabled: Flow<Boolean>
        get() = context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }.map { preferences ->
                preferences[PreferenceKeys.isDarkModeEnabled] ?: false
            }

    override val user: Flow<UserData?>
        get() = context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }.map { preferences ->
                gson.fromJson(preferences[PreferenceKeys.user], UserData::class.java) ?: null
            }
}