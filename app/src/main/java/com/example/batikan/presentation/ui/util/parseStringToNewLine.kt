package com.example.batikan.presentation.ui.util

fun parseStringToNewLine(input: String): String {
    return input.replace("\\n", "\n") // Ganti \\n literal menjadi karakter newline
}