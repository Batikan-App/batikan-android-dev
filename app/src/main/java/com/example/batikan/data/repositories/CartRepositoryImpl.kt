package com.example.batikan.data.repositories

import android.util.Log
import com.example.batikan.data.datasource.remote.CartApiService
import com.example.batikan.data.model.cart.AddItemResponse
import com.example.batikan.data.model.cart.CartDeleteResponse
import com.example.batikan.data.model.cart.CartRequest
import com.example.batikan.data.model.cart.CartResponse
import com.example.batikan.data.model.cart.UpdateItemResponse
import com.example.batikan.domain.repositories.CartRepository
import retrofit2.Response
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val apiService: CartApiService
): CartRepository {
    override suspend fun deleteCartItems(): Result<Unit> {
        return try {
            val response = apiService.deleteCartItem()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed with code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCartData(): Result<CartResponse> {
        return try {
            val response = apiService.getCartItems()

            if(response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    val errorMessage = "Response body is null"
                    Result.failure(Exception(errorMessage))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = "Error: ${response.code()} - ${response.message()} | $errorBody"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("BatikRepository", "Exception: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun addItemToCart(productId: String, quantity: Int): Response<AddItemResponse> {
        val request = CartRequest(productId, quantity)
        return apiService.addItemToCart(request)
    }

    override suspend fun updateCartItem(
        productId: String,
        quantity: Int
    ): Response<UpdateItemResponse> {
        val request = CartRequest(productId, quantity)
        return apiService.updateItemCart(request)
    }
}