package com.olayg.navigationsamplewithauthentication.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PrefStoreImpl(context: Context) : PrefStore {
    private val dataStore by lazy { context.dataStore }

    override fun isUserLoggedIn() = dataStore.data.catch { exception ->
        // dataStore.data throws an IOException if it can't read the data
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[USER_LOGGED_IN_KEY] ?: false }

    override suspend fun toggleUserLoggedIn() {
        dataStore.edit {
            it[USER_LOGGED_IN_KEY] = !(it[USER_LOGGED_IN_KEY] ?: false)
        }
    }

    companion object {
        val USER_LOGGED_IN_KEY = booleanPreferencesKey("user_logged_in")
    }
}