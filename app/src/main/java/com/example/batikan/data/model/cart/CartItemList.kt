package com.example.batikan.data.model.cart

data class CartItemList(
    val id: String,
    val cartItem: List<CartItemData>,
    val totalPrice: Int
)