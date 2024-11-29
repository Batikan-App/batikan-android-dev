package com.example.batikan.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

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

data class BatikResponse(
    val status: String,
    val data: List<Batik>
)

interface BatikApiService {
    @GET("api/batik")
    // Dikosongin parameternya (tinggal CTRL-Z) karena sudah pake AuthInterceptor
    suspend fun getBatikList(
    ): Response<BatikResponse>
}