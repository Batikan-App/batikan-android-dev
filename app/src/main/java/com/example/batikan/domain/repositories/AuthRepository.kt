package com.example.batikan.domain.repositories

import com.example.batikan.data.repositories.AuthRepositoryImpl
import com.example.batikan.data.model.auth.LoginResponse
import com.example.batikan.data.model.auth.RegisterResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authRepository: AuthRepositoryImpl) {
    suspend operator fun invoke(email: String, password: String): Response<LoginResponse> {
        return authRepository.login(email, password)
    }

    suspend fun register(name: String, email: String, phone: String, password: String, verify_password: String): Response<RegisterResponse> {
        return authRepository.register(name, email, phone, password, verify_password)
    }
}