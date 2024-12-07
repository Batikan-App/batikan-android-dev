package com.example.batikan.data.model.order

data class Orders(
    val orderId: String,
    val createdAt: TimeStamp,
    val totalPayment: Int,
    val name: String,
    val phone: String,
    val address: String,
    val status: String,
    val userId: String,
    val updatedAt: TimeStamp,
    val items: List<Item>
)

data class TimeStamp(
    val seconds: Int,
    val nanoseconds: Int
)

data class Item(
    val name: String,
    val img: List<String>,
    val quantity: Int,
    val price: Int
)