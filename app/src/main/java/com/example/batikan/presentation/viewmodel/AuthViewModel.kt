package com.example.batikan.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.batikan.data.datasource.local.DataStoreManager
import com.example.batikan.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.Idle)
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)

    val loginState: StateFlow<LoginState> get() = _loginState
    val logoutState: StateFlow<LogoutState> get() = _logoutState
    val registerState: StateFlow<RegisterState> get() = _registerState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val response = authRepository.login(email, password)
                if (response.isSuccessful) {
                    val body = response.body()
                    val token = response.body()?.token
                    val tokenExp = System.currentTimeMillis() + 24 * 60 * 60 * 1000 // 24 jam dari sekarang
                    if (body != null && !token.isNullOrEmpty()) {
                        dataStoreManager.saveToken(token, tokenExp)
                        Log.d("Login", "Token saved: $token")
                        Log.d("Login", "Exp Time: $tokenExp")
                        _loginState.value = LoginState.Success(body.token)
                    } else {
                        Log.d("Login", "Token is null or empty")
                        _loginState.value = LoginState.Error("Unexpected response format")
                    }
                } else {
                    Log.d("Login", "Login failed: ${response.message()}")
                    _loginState.value = LoginState.Error("Login failed: ${response.message()}")
                }
            } catch (e: HttpException) {
                _loginState.value = LoginState.Error("Network error: ${e.message()}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _logoutState.value = LogoutState.Loading
            try {
                val response = authRepository.logout()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        dataStoreManager.deleteToken()
                        Log.d("Logout", "Token deleted")
                        _logoutState.value = LogoutState.Success(body.message)
                    } else {
                        Log.d("Logout", "Token is not found")
                        _logoutState.value = LogoutState.Error("Token is not found")
                    }
                } else {
                    Log.d("Logout", "Logout failed: ${response.message()}")
                    _logoutState.value = LogoutState.Error("Logout failed: ${response.message()}")
                }
            } catch (e: HttpException) {
                _logoutState.value = LogoutState.Error("Network error: ${e.message()}")
            }
        }
    }

    fun register(name: String, email: String, phone	: String, password: String, verify_password: String){
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            try {
                val response = authRepository.register(name, email, phone, password, verify_password)
                if (response.isSuccessful) {
                    Log.d("Register", "Register success")
                    _registerState.value = RegisterState.Success(message = "Register success")
                } else {
                    Log.d("Register", "Register failed: ${response}")
                    _registerState.value = RegisterState.Error("Register failed: ${response.message()}")
                }

            } catch (e: HttpException) {
                _registerState.value = RegisterState.Error("Network error: ${e.message()}")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class RegisterState {
    object Idle: RegisterState()
    object Loading: RegisterState()
    data class Success(val message: String) : RegisterState()
    data class Error(val message: String) : RegisterState()
}

sealed class LogoutState {
    object Idle : LogoutState()
    object Loading : LogoutState()
    data class Success(val message: String) : LogoutState()
    data class Error(val message: String) : LogoutState()
}

