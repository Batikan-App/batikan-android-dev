package com.example.batikan.data.repositories

import com.example.batikan.data.datasource.remote.UserApiService
import com.example.batikan.data.model.order.MakeOrderResponse
import com.example.batikan.data.model.order.OrderRequest
import com.example.batikan.data.model.order.OrderResponse
import com.example.batikan.data.model.user.UpdateProfileRequest
import com.example.batikan.data.model.user.UpdateProfileResponse
import com.example.batikan.data.model.user.User
import com.example.batikan.data.model.user.UserResponse
import com.example.batikan.domain.repositories.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
): UserRepository {
    override suspend fun getProfile(): User? {
        val response = apiService.getUserProfile()
        if (response.isSuccessful) {
            return response.body()?.data
        } else {
            throw Exception("Error: ${response.message()}")
        }
    }

    override suspend fun updateProfile(name: String, email: String, phone: String): Response<UpdateProfileResponse> {
        val request = UpdateProfileRequest(name, email, phone)
        return apiService.updateProfile(request)
    }

    override suspend fun getUserOrders(): OrderResponse {
        val response = apiService.getOrder()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Error: ${response.message()}")
        }
    }

    override suspend fun makeOrder(name: String, phone: String, address: String): Response<MakeOrderResponse>  {
        val request = OrderRequest(name, phone, address)
        return apiService.makeOrder(request)
    }
}