package com.olayg.navigationsamplewithauthentication.datastore

import kotlinx.coroutines.flow.Flow

interface PrefStore {

    fun isUserLoggedIn(): Flow<Boolean>

    suspend fun toggleUserLoggedIn()
}