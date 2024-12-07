package com.example.batikan.domain.repositories

import com.example.batikan.data.repositories.AuthRepositoryImpl
import com.example.batikan.data.model.auth.LoginResponse
import com.example.batikan.data.model.auth.LogoutResponse
import com.example.batikan.data.model.auth.RegisterResponse
import retrofit2.Response
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(email: String, password: String): Response<LoginResponse>

    suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        verify_password: String
    ): Response<RegisterResponse>

    suspend fun logout(): Response<LogoutResponse>
}