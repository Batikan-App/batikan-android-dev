package com.example.batikan.data.model.cart

data class CartItemData(
    val id: String,
    val name: String,
    val img: List<String>,
    val quantity: Int,
    val price: Int
)