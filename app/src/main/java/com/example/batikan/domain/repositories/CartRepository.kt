package com.example.batikan.domain.repositories

import com.example.batikan.data.model.cart.AddItemResponse
import com.example.batikan.data.model.cart.CartDeleteResponse
import com.example.batikan.data.model.cart.CartItemList
import com.example.batikan.data.model.cart.CartResponse
import com.example.batikan.data.model.cart.UpdateItemResponse
import retrofit2.Response

interface CartRepository {
    suspend fun getCartData(): Result<CartResponse>

    suspend fun addItemToCart(productId: String, quantity: Int): Response<AddItemResponse>

    suspend fun updateCartItem(productId: String, quantity: Int): Response<UpdateItemResponse>

    suspend fun deleteCartItems(): Result<Unit>
}