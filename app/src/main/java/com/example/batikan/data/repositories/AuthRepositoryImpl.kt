package com.example.batikan.data.repositories

import com.example.batikan.data.datasource.remote.AuthApiService
import com.example.batikan.data.model.auth.LoginRequest
import com.example.batikan.data.model.auth.LoginResponse
import com.example.batikan.data.model.auth.RegisterRequest
import com.example.batikan.data.model.auth.RegisterResponse
import com.example.batikan.data.model.auth.LogoutResponse
import com.example.batikan.domain.repositories.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService
): AuthRepository {
    override suspend fun login(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        return apiService.login(request)
    }

    override suspend fun register(name: String, email: String, phone	: String, password: String, verify_password: String): Response<RegisterResponse> {
        val request = RegisterRequest(name, email, phone, password, verify_password)
        return apiService.register(request)
    }

    override suspend fun logout(): Response<LogoutResponse> {
        return apiService.logout()
    }
}