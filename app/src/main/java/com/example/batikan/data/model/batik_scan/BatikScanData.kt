package com.example.batikan.data.model.batik_scan

import com.example.batikan.data.model.batik_product.BatikDetails

data class BatikScanData(
    val batikId: String,
    val confidence: String,
    val data: BatikDetails
)