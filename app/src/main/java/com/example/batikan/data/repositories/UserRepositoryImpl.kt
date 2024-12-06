package com.example.batikan.data.repositories

import com.example.batikan.data.datasource.remote.UserApiService
import com.example.batikan.data.model.order.OrderResponse
import com.example.batikan.data.model.user.UpdateProfileRequest
import com.example.batikan.data.model.user.UpdateProfileResponse
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

    suspend fun updateProfile(name: String, email: String, phone	: String): Response<UpdateProfileResponse> {
        val request = UpdateProfileRequest(name, email, phone)
        return apiService.updateProfile(request)
    }

    suspend fun getUserOrders(): OrderResponse {
        val response = apiService.getOrder()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Error: ${response.message()}")
        }

    }
}