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
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.Idle)

    val userState: StateFlow<UserState> get() = _userState
    val updateState: StateFlow<UpdateState> get() = _updateState


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

    fun updateProfile(name: String, email: String, phone: String) {
        viewModelScope.launch {
            _updateState.value = UpdateState.Loading
            try {
                val response = userRepository.updateProfile(name, email, phone)
                if (response.isSuccessful) {
//                    val body = response.body()
                    Log.d("Update", "Update success")
                    _updateState.value = UpdateState.Success(message = "Update success")
                } else {
                    Log.d("Update", "Update failed: ${response}")
                    _updateState.value = UpdateState.Error("Update failed: ${response.message()}")
                }
            } catch (e: HttpException) {
                _updateState.value = UpdateState.Error("Network error: ${e.message()}")
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

sealed class UpdateState {
    object Idle : UpdateState()
    object Loading : UpdateState()
    data class Success(val message: String) : UpdateState()
    data class Error(val message: String) : UpdateState()
}