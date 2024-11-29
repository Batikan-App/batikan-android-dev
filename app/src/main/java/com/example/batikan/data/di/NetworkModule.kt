package com.example.batikan.data.di

import android.content.Context
import androidx.datastore.dataStore
import com.example.batikan.data.local.DataStoreManager
import com.example.batikan.data.remote.AuthApiService
import com.example.batikan.data.remote.AuthInterceptor
import com.example.batikan.data.remote.BatikApiService
import com.example.batikan.data.repositories.AuthRepositoryImpl
import com.example.batikan.data.repositories.BatikRepositoryImpl
import com.example.batikan.domain.repositories.AuthRepository
import com.example.batikan.domain.repositories.BatikRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://batikan-backend-30189848328.asia-southeast2.run.app/"

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
    fun provideAuthRepositoryImpl(apiService: AuthApiService): AuthRepositoryImpl {
        return AuthRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(repository: AuthRepositoryImpl): AuthRepository {
        return AuthRepository(repository)
    }

    /**
     * Untuk build Batik
     */
    @Provides
    @Singleton
    fun provideBatikApiService(retrofit: Retrofit): BatikApiService = retrofit.create(BatikApiService::class.java)

    @Provides
    @Singleton
    fun provideBatikRepositoryImpl(apiService: BatikApiService): BatikRepositoryImpl {
        return BatikRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideBatikRepository(repository: BatikRepositoryImpl): BatikRepository {
        return BatikRepository(repository)
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