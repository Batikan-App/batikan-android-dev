package com.example.batikan.data.datasource.remote

import com.example.batikan.data.model.cart.AddItemResponse
import com.example.batikan.data.model.cart.CartRequest
import com.example.batikan.data.model.cart.CartResponse
import com.example.batikan.data.model.cart.UpdateItemResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApiService {
    @GET("api/user/cart")
    suspend fun getCartItems(): Response<CartResponse>

    @POST("api/user/cart")
    suspend fun addItemToCart(
        @Body request: CartRequest
    ): Response<AddItemResponse>

    @PATCH("api/user/cart")
    suspend fun updateItemCart(
        @Body request: CartRequest
    ): Response<UpdateItemResponse>
}