package com.example.myandrostory.utils
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//private val Context.dataStore by preferencesDataStore("user_prefs")
//
//class SessionManager(context: Context) {
//    private val dataStore = context.dataStore
//
//    companion object {
//        val USER_TOKEN_KEY = stringPreferencesKey("USER_TOKEN")
//        val USER_SESSION_KEY = booleanPreferencesKey("USER_SESSION")
//    }
//
//    suspend fun storeUser(token: String, session: Boolean) {
//        dataStore.edit {
//            it[USER_TOKEN_KEY] = token
//            it[USER_SESSION_KEY] = session
//        }
//    }
//
//    val userTokenFlow: Flow<String> = dataStore.data.map {
//        it[USER_TOKEN_KEY] ?: ""
//    }
//
//    val userSessionFlow: Flow<Boolean> = dataStore.data.map {
//        it[USER_SESSION_KEY] ?: false
//    }
//}