package com.example.myandrostory.data.di

import android.content.Context
import com.example.myandrostory.data.Repository
import com.example.myandrostory.data.api.ApiConfig
import com.example.myandrostory.utils.UserPreferences
import com.example.myandrostory.utils.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking { pref.getUserToken().first() }
        val apiService = ApiConfig.getApiService(user)
        return Repository.getInstance(apiService, pref)
    }
}