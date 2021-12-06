package com.olayg.navigationsamplewithauthentication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olayg.navigationsamplewithauthentication.model.Browse
import com.olayg.navigationsamplewithauthentication.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BrowseViewModel : ViewModel() {

    private val _mockList = MutableLiveData<DataState<List<Browse>>>()
    val mockList: LiveData<DataState<List<Browse>>> get() = _mockList

    init {
        viewModelScope.launch(Dispatchers.IO) { fakeFetchMockData() }
    }

    private suspend fun fakeFetchMockData() {
        _mockList.postValue(DataState.Loading)
        delay(5000)
        val mockList = listOf(
            Browse(0, "Title 0"),
            Browse(1, "Title 1"),
            Browse(2, "Title 2"),
            Browse(3, "Title 3"),
            Browse(4, "Title 4"),
            Browse(5, "Title 5"),
            Browse(6, "Title 6"),
            Browse(7, "Title 7"),
            Browse(8, "Title 8"),
            Browse(9, "Title 9"),
            Browse(10, "Title 10"),
        )
        _mockList.postValue(DataState.Success(mockList))
    }

}