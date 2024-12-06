package com.example.batikan.data.repositories

import com.example.batikan.data.datasource.remote.UserApiService
import com.example.batikan.data.model.user.User
import com.example.batikan.data.model.user.UserResponse
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
) {
    suspend fun getProfile(): User? {
        val response = apiService.getUserProfile()
        if (response.isSuccessful) {
            return response.body()?.data
        } else {
            throw Exception("Error: ${response.message()}")
        }
    }
}