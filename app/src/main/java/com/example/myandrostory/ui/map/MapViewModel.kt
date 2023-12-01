package com.example.myandrostory.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myandrostory.data.Repository
import com.example.myandrostory.data.Result
import com.example.myandrostory.data.response.StoryResponse
import kotlinx.coroutines.launch

class MapViewModel(private val repository: Repository) : ViewModel() {
    val storyResult: MutableLiveData<Result<StoryResponse>> = MutableLiveData()

    fun getStoriesWithLocation() {
        viewModelScope.launch {
            try {
                val response = repository.getStoriesWithLocation()
                if (response.isSuccessful) {
                    storyResult.postValue(Result.Success(response.body()))
                } else {
                    storyResult.postValue(Result.Error(response.body()?.message))
                }
            } catch (ex: Exception) {
                storyResult.value = Result.Error(ex.message)
            }
        }
    }
}