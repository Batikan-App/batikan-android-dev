package com.example.batikan.data.model.auth

data class RegisterRequest(val name: String, val email: String, val phone	: String, val password: String, val verify_password: String)
