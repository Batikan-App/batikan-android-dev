package com.example.batikan.domain.repositories

import com.example.batikan.data.model.user.User
import com.example.batikan.data.repositories.UserRepositoryImpl
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
) {
    suspend fun getProfile(): User? {
        return userRepositoryImpl.getProfile()
    }
}