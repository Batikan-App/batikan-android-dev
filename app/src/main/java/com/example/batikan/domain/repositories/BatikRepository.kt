package com.example.batikan.domain.repositories

import com.example.batikan.data.remote.Batik
import com.example.batikan.data.repositories.BatikRepositoryImpl
import javax.inject.Inject

class BatikRepository @Inject constructor(
    private val batikRepositoryImpl: BatikRepositoryImpl
) {
    suspend fun getBatik(): List<Batik> {
        return batikRepositoryImpl.getBatik()
    }
}