package com.olayg.navigationsamplewithauthentication.viewmodel

import android.app.Application
import androidx.annotation.NavigationRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.olayg.navigationsamplewithauthentication.R
import com.olayg.navigationsamplewithauthentication.datastore.PrefStore
import com.olayg.navigationsamplewithauthentication.datastore.PrefStoreImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val prefs: PrefStore by lazy { PrefStoreImpl(app) }

    private val _startingPointCalculated = MutableLiveData<@NavigationRes Int?>(null)
    val startingPointCalculated: LiveData<Int?> get() = _startingPointCalculated
    var userLoggedIn: Boolean = false

    init {
        viewModelScope.launch(Dispatchers.Default) {
            prefs.isUserLoggedIn().collect {
                userLoggedIn = it
                _startingPointCalculated.postValue(if (it) R.id.nav_graph else R.id.destination_login)
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.Default) { prefs.toggleUserLoggedIn() }
    }
}