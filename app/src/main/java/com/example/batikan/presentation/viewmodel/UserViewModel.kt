package com.example.batikan.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.batikan.data.model.batik_product.BatikList
import com.example.batikan.data.model.user.User
import com.example.batikan.domain.repositories.UserRepository
import com.example.batikan.presentation.viewmodel.BatikState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState: StateFlow<UserState> get() = _userState

    fun fetchUserProfile() {
        viewModelScope.launch {
            Log.d("UserViewModel", "Fetching user profile data...")
            _userState.value = UserState.Loading

            try {
                val userProfile = userRepository.getProfile()
                if (userProfile != null) {
                    Log.d("UserViewModel", "API Response: User fetched successfully")
                    _userState.value = UserState.Success(userProfile)
                } else {
                    Log.d("UserViewModel", "API Response: User data is null")
                    _userState.value = UserState.Error("User not found")
                }
            } catch (e: Exception) {
                Log.d("UserViewModel", "Exception: ${e.message}")
                _userState.value = UserState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}


sealed class UserState {
    object Idle : UserState()
    object Loading : UserState()
    data class Success(val data: User) : UserState()
    data class Error(val message: String) : UserState()
}