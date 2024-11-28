package com.example.batikan.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")
object DataStoreManager {
    private val TOKEN_KEY = stringPreferencesKey("auth_token")

    suspend fun saveToken(context: Context, token: String) {
        context.dataStore.edit { preferences -> preferences[TOKEN_KEY] = token }
    }

    fun getToken(context: Context) = context.dataStore.data.map { it[TOKEN_KEY] ?: "" }
}

//private val Context.dataStore by preferencesDataStore("user_prefs")
//
//class DataStoreManager(private val context: Context) {
//    companion object {
//        private val TOKEN_KEY = stringPreferencesKey("auth_token")
//    }
//
//    val authToken: Flow<String?> = context.dataStore.data.map { preferences ->
//        preferences[TOKEN_KEY]
//    }
//
//    suspend fun saveAuthToken(token: String) {
//        context.dataStore.edit { preferences ->
//            preferences[TOKEN_KEY] = token
//        }
//    }
//
//    suspend fun clearAuthToken() {
//        context.dataStore.edit { preferences ->
//            preferences.remove(TOKEN_KEY)
//        }
//    }
//}