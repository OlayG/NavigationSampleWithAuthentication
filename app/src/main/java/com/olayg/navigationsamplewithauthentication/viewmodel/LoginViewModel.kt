package com.olayg.navigationsamplewithauthentication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.olayg.navigationsamplewithauthentication.datastore.PrefStore
import com.olayg.navigationsamplewithauthentication.datastore.PrefStoreImpl
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val prefs: PrefStore by lazy { PrefStoreImpl(app) }

    fun login(username: String, password: String) {
        viewModelScope.launch { prefs.toggleUserLoggedIn() }
    }
}