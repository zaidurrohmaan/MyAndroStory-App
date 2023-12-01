package com.example.myandrostory.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myandrostory.data.Repository
import com.example.myandrostory.data.response.StoryItem
import kotlinx.coroutines.launch

class StoryViewModel(private val repository: Repository): ViewModel() {
    val story: LiveData<PagingData<StoryItem>> =
        repository.getStories().cachedIn(viewModelScope)

    val token: LiveData<String> = repository.getUserToken().asLiveData()

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            repository.saveUserToken(token)
        }
    }

    fun saveUserSession(isLogin: Boolean) {
        viewModelScope.launch {
            repository.saveUserSession(isLogin)
        }
    }
}