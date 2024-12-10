package com.example.batikan.data.datasource.remote

import com.example.batikan.data.model.cart.CartResponse
import com.example.batikan.data.model.order.MakeOrderResponse
import com.example.batikan.data.model.order.OrderRequest
import com.example.batikan.data.model.order.OrderResponse
import com.example.batikan.data.model.user.UpdateProfileRequest
import com.example.batikan.data.model.user.UpdateProfileResponse
import com.example.batikan.data.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApiService {
    @GET("api/user/profile")
    suspend fun getUserProfile(
    ): Response<UserResponse>

    @PUT("api/user/profile")
    suspend fun updateProfile(
        @Body request: UpdateProfileRequest
    ): Response<UpdateProfileResponse>

    @GET("api/user/cart")
    suspend fun getCart(

    ): Response<CartResponse>

    @GET("api/user/order")
    suspend fun getOrder(

    ): Response<OrderResponse>

    @POST("api/user/order")
    suspend fun makeOrder(
        @Body request: OrderRequest
    ): Response<MakeOrderResponse>
}