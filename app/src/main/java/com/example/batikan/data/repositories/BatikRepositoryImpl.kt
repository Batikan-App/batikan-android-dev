package com.example.batikan.data.repositories

import com.example.batikan.data.remote.Batik
import com.example.batikan.data.remote.BatikApiService
import javax.inject.Inject

class BatikRepositoryImpl @Inject constructor(
    private val apiService: BatikApiService
) {
    suspend fun getBatik(): List<Batik> {
        val response = apiService.getBatikList() // Tidak perlu lagi menyisipkan token manual
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Error: ${response.message()}") // Tangani error
        }
    }
}

