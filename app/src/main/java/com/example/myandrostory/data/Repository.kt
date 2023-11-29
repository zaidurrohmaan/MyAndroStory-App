package com.example.myandrostory.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.myandrostory.data.api.ApiConfig
import com.example.myandrostory.data.api.ApiService
import com.example.myandrostory.data.response.LoginResponse
import com.example.myandrostory.data.response.RegisterResponse
import com.example.myandrostory.data.response.StoryItem
import com.example.myandrostory.data.response.StoryResponse
import com.example.myandrostory.utils.UserPreferences
import kotlinx.coroutines.flow.first
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(private val apiService: ApiService, private val pref: UserPreferences) {

    suspend fun registerUser(name: String, email: String, password: String): Response<RegisterResponse>? {
        return apiService.userRegister(name, email, password)
    }

    suspend fun loginUser(email: String, password: String): Response<LoginResponse>? {
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

//        var token: String = pref.getUserToken().first()
//        return ApiConfig.getApiService(token).getStories()
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreferences
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(apiService, pref)
        }.also { instance = it }
    }

    //    private val result = MediatorLiveData<Result<RegisterResponse>>()
//    fun registerUser(name: String, email: String, password: String): LiveData<Result<RegisterResponse>>{
//        //result.value = Result.Loading
//        val client = apiService.userRegister(name, email, password)
//        client.enqueue(object : Callback<RegisterResponse> {
//            override fun onResponse(
//                call: Call<RegisterResponse>,
//                response: Response<RegisterResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val registerStatus = response.body()
//                    if(registerStatus != null){
//                        result.value = Result.Success(registerStatus)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
//                result.value = Result.Error(t.message.toString())
//            }
//        })
//        return result
//    }

}