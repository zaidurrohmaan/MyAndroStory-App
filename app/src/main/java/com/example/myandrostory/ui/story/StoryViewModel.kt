package com.example.myandrostory.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myandrostory.data.Repository
import com.example.myandrostory.data.response.StoryItem

class StoryViewModel(repository: Repository): ViewModel() {
    val story: LiveData<PagingData<StoryItem>> =
        repository.getStories().cachedIn(viewModelScope)
}