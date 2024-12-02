package com.example.batikan.data.datasource.remote

import com.example.batikan.data.model.batik_product.BatikResponse
import com.example.batikan.data.model.batik_scan.BatikScanResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface BatikApiService {
    @GET("api/batik")
    // Dikosongin parameternya (tinggal CTRL-Z) karena sudah pake AuthInterceptor
    suspend fun getBatikList(
    ): Response<BatikResponse>

    @Multipart
    @POST("api/batik/scan")
    suspend fun scanBatik(
        @Part image: MultipartBody.Part
    ): Response<BatikScanResponse>
}