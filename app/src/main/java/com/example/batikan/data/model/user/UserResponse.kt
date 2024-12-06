package com.example.batikan.data.model.user

data class UserResponse(
    val status: String,
    val data: User?,
    val message: String?
)