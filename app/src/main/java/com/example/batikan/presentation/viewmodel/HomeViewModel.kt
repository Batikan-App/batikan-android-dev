package com.example.batikan.presentation.viewmodel

import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.batikan.data.remote.Batik
import com.example.batikan.data.remote.BatikApiService
import com.example.batikan.domain.repositories.BatikRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val batikApiService: BatikApiService,
    private val datastore: DataStore<Preferences>
) : ViewModel() {

    private val _batikList = MutableStateFlow<List<Batik>>(emptyList())
    val batikList: StateFlow<List<Batik>> = _batikList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchBatikList(): List<Batik> {

        viewModelScope.launch {
            try {
                val token = datastore.data.map { preferences ->
                    preferences[stringPreferencesKey("auth_token")] ?: ""
                }.first()

                if (token.isEmpty()) throw Exception("Token not found")

                val response = batikApiService.getBatikList(token)
                if (response.status != "success") throw Exception("Failed to fetch data")

                _isLoading.value = true
                val data = batikApiService.getBatikList(token = token)
                _batikList.value = data.data

            } catch (e: Exception) {
                // TODO: Not yet implemented
            } finally {
                _isLoading.value = false
            }
        }
        return emptyList()
    }
}