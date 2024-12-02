package com.example.batikan.domain.repositories

import com.example.batikan.data.repositories.AuthRepositoryImpl
import com.example.batikan.data.model.auth.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authRepository: AuthRepositoryImpl) {
    suspend operator fun invoke(email: String, password: String): Response<LoginResponse> {
        return authRepository.login(email, password)
    }
}