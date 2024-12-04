package com.example.batikan.data.repositories

import com.example.batikan.data.datasource.remote.AuthApiService
import com.example.batikan.data.model.auth.LoginRequest
import com.example.batikan.data.model.auth.LoginResponse
import com.example.batikan.data.model.auth.LogoutResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val apiService: AuthApiService) {
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        return apiService.login(request)
    }

    suspend fun logout(): Response<LogoutResponse> {
        return apiService.logout()
    }
}