package com.example.batikan.domain.repositories

import android.media.Image
import com.example.batikan.data.model.batik_product.BatikList
import com.example.batikan.data.model.batik_scan.BatikScanResponse
import com.example.batikan.data.repositories.BatikRepositoryImpl
import java.io.File
import javax.inject.Inject

class BatikRepository @Inject constructor(
    private val batikRepositoryImpl: BatikRepositoryImpl
) {
    suspend fun getBatik(): List<BatikList> {
        return batikRepositoryImpl.getBatik()
    }

    suspend operator fun invoke(imageFile: File): Result<BatikScanResponse> {
        return batikRepositoryImpl.scanBatik(imageFile)
    }

    suspend fun getBatikDetail(batikId: String): List<BatikList> {
        return batikRepositoryImpl.getBatikDetail()
    }
}