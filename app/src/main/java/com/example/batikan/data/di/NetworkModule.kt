package com.example.batikan.data.di

import android.content.Context
import com.example.batikan.data.Constants
import com.example.batikan.data.datasource.local.DataStoreManager
import com.example.batikan.data.datasource.remote.AuthApiService
import com.example.batikan.data.datasource.remote.AuthInterceptor
import com.example.batikan.data.datasource.remote.BatikApiService
import com.example.batikan.data.datasource.remote.CartApiService
import com.example.batikan.data.datasource.remote.UserApiService
import com.example.batikan.data.repositories.AuthRepositoryImpl
import com.example.batikan.data.repositories.BatikRepositoryImpl
import com.example.batikan.data.repositories.CartRepositoryImpl
import com.example.batikan.data.repositories.UserRepositoryImpl
import com.example.batikan.domain.repositories.AuthRepository
import com.example.batikan.domain.repositories.BatikRepository
import com.example.batikan.domain.repositories.CartRepository
import com.example.batikan.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = Constants.BATIKAN_BASE_URL

    /**
     * Untuk AuthInterceptor
     */
    @Provides
    @Singleton
    fun provideAuthInterceptor(dataStoreManager: DataStoreManager): AuthInterceptor {
        return AuthInterceptor(dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor) // Tambahkan AuthInterceptor
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Logging untuk debugging
            })
            .build()
    }

    /**
     * Untuk menyediakan Retrofit instance
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // Gunakan OkHttpClient dengan interceptor
            .build()
    }

    /**
     * Untuk build Auth
     */
    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService = retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(apiService: AuthApiService): AuthRepository {
        return AuthRepositoryImpl(apiService)
    }

    /**
     * Untuk build Batik
     */
    @Provides
    @Singleton
    fun provideBatikApiService(retrofit: Retrofit): BatikApiService = retrofit.create(
        BatikApiService::class.java)

    @Provides
    @Singleton
    fun provideBatikRepositoryImpl(apiService: BatikApiService): BatikRepository {
        return BatikRepositoryImpl(apiService)
    }

    /**
     * Untuk build User
     */
    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService = retrofit.create(
        UserApiService::class.java)

    @Provides
    @Singleton
    fun provideUserRepositoryImpl(apiService: UserApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }

    /**
     * Untuk cart
     */
    @Provides
    @Singleton
    fun provideCartApiService(retrofit: Retrofit): CartApiService = retrofit.create(
        CartApiService::class.java
    )

    @Provides
    @Singleton
    fun provideCartRepositoryImpl(apiService: CartApiService): CartRepository {
        return CartRepositoryImpl(apiService)
    }

    /**
     * Untuk build DataStore
     */
    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}