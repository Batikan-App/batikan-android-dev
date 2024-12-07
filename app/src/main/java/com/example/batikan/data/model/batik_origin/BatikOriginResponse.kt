package com.example.batikan.data.model.batik_origin

import com.example.batikan.data.model.batik_product.BatikDetails

data class BatikOriginResponse(
    val status: String,
    val data: List<BatikOriginDetails>
)
