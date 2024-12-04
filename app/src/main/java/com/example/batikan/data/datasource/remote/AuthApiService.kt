package com.example.batikan.data.datasource.remote

import com.example.batikan.data.model.auth.LoginRequest
import com.example.batikan.data.model.auth.LoginResponse
import com.example.batikan.data.model.auth.LogoutResponse
import com.example.batikan.data.model.auth.RegisterRequest
import com.example.batikan.data.model.auth.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface AuthApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/auth/logout")
    suspend fun logout(): Response<LogoutResponse>

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}