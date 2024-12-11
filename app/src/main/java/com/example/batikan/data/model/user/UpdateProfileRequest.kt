package com.example.batikan.data.model.user

data class UpdateProfileRequest(
    val name: String,
    val email: String,
    val phone: String,
//    val password: String?,
//    val verify_password: String?
)