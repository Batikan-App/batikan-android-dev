package com.example.batikan.data.repositories

import com.example.batikan.data.remote.AuthApiService
import com.example.batikan.data.remote.LoginRequest
import com.example.batikan.data.remote.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val apiService: AuthApiService) {
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        return apiService.login(request)
    }
}