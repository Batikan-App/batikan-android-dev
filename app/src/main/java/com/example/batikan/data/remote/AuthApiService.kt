package com.example.batikan.data.remote

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val status: String, val message: String, val token: String)

interface AuthApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}