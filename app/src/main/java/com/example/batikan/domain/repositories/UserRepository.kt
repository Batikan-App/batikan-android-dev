package com.example.batikan.domain.repositories

import com.example.batikan.data.model.user.UpdateProfileResponse
import com.example.batikan.data.model.user.User
import com.example.batikan.data.repositories.UserRepositoryImpl
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
) {
    suspend fun getProfile(): User? {
        return userRepositoryImpl.getProfile()
    }

    suspend fun updateProfile(name: String, email: String, phone: String): Response<UpdateProfileResponse> {
        return userRepositoryImpl.updateProfile(name, email, phone)
    }
}