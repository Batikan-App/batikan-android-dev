package com.example.batikan.domain.repositories

import android.util.Log
import com.example.batikan.data.model.order.MakeOrderResponse
import com.example.batikan.data.model.order.OrderResponse
import com.example.batikan.data.model.user.UpdateProfileResponse
import com.example.batikan.data.model.user.User
import com.example.batikan.data.repositories.UserRepositoryImpl
import retrofit2.Response
import javax.inject.Inject

interface UserRepository{
    suspend fun getProfile(): User?

    suspend fun updateProfile(name: String, email: String, phone: String): Response<UpdateProfileResponse>

    suspend fun getUserOrders(): OrderResponse

    suspend fun makeOrder(name: String, phone: String, address: String): Response<MakeOrderResponse>
}