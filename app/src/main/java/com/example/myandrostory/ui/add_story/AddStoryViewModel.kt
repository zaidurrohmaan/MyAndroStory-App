package com.example.myandrostory.ui.add_story

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myandrostory.data.Repository
import com.example.myandrostory.data.Result
import com.example.myandrostory.data.response.AddStoryResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: Repository) : ViewModel() {
    val addStoryResult: MutableLiveData<Result<AddStoryResponse>> = MutableLiveData()
    var currentUri = MutableLiveData<Uri>()
    fun saveUri(uri: Uri) {
        currentUri.postValue(uri)
    }

    fun uploadStory(multipartBody: MultipartBody.Part, requestBody: RequestBody) {
        viewModelScope.launch {
            try {
                val response = repository.uploadStory(multipartBody, requestBody)
                if (response.isSuccessful) {
                    addStoryResult.postValue(Result.Success(response.body()))
                } else {
                    addStoryResult.postValue(Result.Error(response.body()?.message))
                }
            } catch (ex: Exception) {
                addStoryResult.value = Result.Error(ex.message)
            }
        }
    }
}