package com.example.batikan.data.datasource.remote

import com.example.batikan.data.datasource.local.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

//class AuthInterceptor(
//    private val accessToken: String?
//): Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request().newBuilder()
//
//        request.addHeader("Authorization", "Bearer $accessToken")
//
//        return chain.proceed(request.build())
//    }
//}

class AuthInterceptor(private val dataStoreManager: DataStoreManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Mendapatkan token di dalam coroutine
        val token = runBlocking { dataStoreManager.getToken() }

        // Menambahkan header Authorization
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", token)
            .build()

        return chain.proceed(newRequest)
    }
}