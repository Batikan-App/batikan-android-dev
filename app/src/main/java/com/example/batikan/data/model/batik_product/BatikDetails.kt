package com.example.batikan.data.model.batik_product

data class BatikDetails(
    val name: String,
    val img: List<String>,
    val origin: String,
    val price: Int,
    val desc: String,
    val sold: Int,
    val stock: Int
)