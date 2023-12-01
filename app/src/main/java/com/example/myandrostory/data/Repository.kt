package com.example.myandrostory.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.myandrostory.data.api.ApiService
import com.example.myandrostory.data.response.AddStoryResponse
import com.example.myandrostory.data.response.LoginResponse
import com.example.myandrostory.data.response.RegisterResponse
import com.example.myandrostory.data.response.StoryItem
import com.example.myandrostory.data.response.StoryResponse
import com.example.myandrostory.utils.UserPreferences
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class Repository private constructor(
    private val apiService: ApiService,
    private val pref: UserPreferences,
) {

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
    ): Response<RegisterResponse> {
        return apiService.userRegister(name, email, password)
    }

    suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        return apiService.userLogin(email, password)
    }

    fun getStories(): LiveData<PagingData<StoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    suspend fun getStoriesWithLocation(): Response<StoryResponse> {
        return apiService.getStoriesWithLocation()
    }

    suspend fun uploadStory(
        multipartBody: MultipartBody.Part,
        requestBody: RequestBody,
    ): Response<AddStoryResponse> {
        return apiService.uploadStory(multipartBody, requestBody)
    }

    fun getUserToken(): Flow<String> {
        return pref.getUserToken()
    }

    suspend fun saveUserToken(token: String) {
        pref.saveUserToken(token)
    }

    suspend fun saveUserSession(isLogin: Boolean) {
        pref.saveUserSession(isLogin)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences,
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(apiService, pref)
        }.also { instance = it }
    }
}