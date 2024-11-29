package com.example.batikan.data.remote

import retrofit2.http.GET
import retrofit2.http.Header

data class BatikResponse(
    val status: String,
    val data: List<Batik>
)

data class Batik(
    val id: String,
    val data: BatikDetails
)

data class BatikDetails(
    val name: String,
    val img: String,
    val origin: String,
    val price: Int,
    val desc: String,
    val sold: Int,
    val stock: Int
)

interface BatikApiService {
    @GET("/api/batik")
    suspend fun getBatikList(
        @Header("Authorization") token: String
    ): BatikResponse
}