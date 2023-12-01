package com.example.myandrostory.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
    private val USER_SESSION_KEY = booleanPreferencesKey("user_session")

    fun getUserToken(): Flow<String> {
        return dataStore.data.map {
            it[USER_TOKEN_KEY] ?: ""
        }
    }

    fun getUserSession(): Flow<Boolean> {
        return dataStore.data.map {
            it[USER_SESSION_KEY] ?: false
        }
    }

    suspend fun saveUserToken(userToken: String) {
        dataStore.edit {
            it[USER_TOKEN_KEY] = userToken
        }
    }

    suspend fun saveUserSession(isLogin: Boolean) {
        dataStore.edit {
            it[USER_SESSION_KEY] = isLogin
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}