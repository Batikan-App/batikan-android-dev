package com.example.batikan.data.di

import com.example.batikan.data.remote.ApiService
import com.example.batikan.data.remote.BatikApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
//    @Provides
//    fun provideOkHttpClient(
//        headerInterceptor: HeaderInterceptor,
//    ): OkHttpClient = OkHttpClient.Builder()
//        .callTimeout(TIME_OUT, TimeUnit.MINUTES)
//        .connectTimeout(TIME_OUT, TimeUnit.MINUTES)
//        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//        .addInterceptor(headerInterceptor)
//        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://batikan-backend-30189848328.asia-southeast2.run.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    fun provideBatikApiService(retrofit: Retrofit): BatikApiService = retrofit.create(BatikApiService::class.java)
}