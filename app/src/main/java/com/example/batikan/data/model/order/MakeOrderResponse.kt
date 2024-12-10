package com.example.batikan.data.model.order

data class MakeOrderResponse(
    val status: String,
    val message: String,
    val data: MakeOrderData
)

data class MakeOrderData(
    val orderId: String,
    val totalPayment: Int
)