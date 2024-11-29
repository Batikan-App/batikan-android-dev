//package com.example.batikan.data.di
//
//import com.example.batikan.data.remote.BatikRepositoryImpl
//import com.example.batikan.domain.repositories.BatikRepository
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object RepositoryModule {
//
//    @Provides
//    @Singleton
//    fun provideBatikRepository(): BatikRepository {
//        return BatikRepositoryImpl() // Ganti dengan implementasi Anda
//    }
//}
