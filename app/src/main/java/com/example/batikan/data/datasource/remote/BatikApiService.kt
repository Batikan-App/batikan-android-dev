package com.example.batikan.data.datasource.remote

import com.example.batikan.data.model.batik_details.BatikDetailsResponse
import com.example.batikan.data.model.batik_origin.BatikOriginResponse
import com.example.batikan.data.model.batik_product.BatikResponse
import com.example.batikan.data.model.batik_scan.BatikScanResponse
import com.example.batikan.data.model.batik_search.BatikSearchResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("api/batik/{id}")
    suspend fun getBatikDetail(
        @Path("id") id: String
    ): Response<BatikDetailsResponse>

    @GET("api/batik/origin")
    suspend fun getBatikOrigin(
        @Query("q") query: String
    ): Response<BatikOriginResponse>

    @GET("api/batik/search")
    suspend fun searchBatik(
        @Query("q") query: String
    ): Response<BatikSearchResponse>
}