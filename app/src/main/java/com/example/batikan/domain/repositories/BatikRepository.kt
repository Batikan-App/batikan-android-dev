package com.example.batikan.domain.repositories

import com.example.batikan.data.model.batik_details.BatikDetailsResponse
import com.example.batikan.data.model.batik_origin.BatikOriginDetails
import com.example.batikan.data.model.batik_product.BatikList
import com.example.batikan.data.model.batik_scan.BatikScanResponse
import java.io.File

interface BatikRepository {
    suspend fun getBatik(): List<BatikList>

    suspend fun scanBatik(imageFile: File): Result<BatikScanResponse>

    suspend fun getBatikDetail(batikId: String): Result<BatikDetailsResponse>

    suspend fun getBatikOrigin(origin: String): List<BatikOriginDetails>
}