package com.example.batikan.data.datasource.local

import android.content.Context
import android.util.Log
import androidx.core.content.contentValuesOf
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val EXPIRATION_DATE_KEY = longPreferencesKey("expired_at")
    }

    suspend fun saveToken(token: String, tokenExp: Long) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
            preferences[EXPIRATION_DATE_KEY] = tokenExp
        }
    }
    // Perbaiki getToken() untuk mengumpulkan nilai dari Flow
    suspend fun getToken(): String {
        // Menggunakan first() untuk mendapatkan nilai pertama dari Flow
        val preferences = context.dataStore.data.first()
        return preferences[TOKEN_KEY] ?: ""  // Kembalikan token atau string kosong jika tidak ditemukan
    }

    suspend fun getTokenExp(): Long? {
        return context.dataStore.data.map { preferences ->
            preferences[EXPIRATION_DATE_KEY]
        }.firstOrNull()
    }

    // Alternatif jika ingin Flow-based
    fun getTokenFlow(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(EXPIRATION_DATE_KEY)
        }
    }
}

//suspend fun saveToken(context: Context, token: String) {
//    context.dataStore.edit { preferences -> preferences[TOKEN_KEY] = token }
//}

//    fun getToken(context: Context) = context.dataStore.data.map { it[TOKEN_KEY] ?: "" }
// }

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