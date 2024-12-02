package com.example.batikan.presentation.ui.screens.on_boarding_screen

import androidx.annotation.DrawableRes
import com.example.batikan.R

sealed class OnboardingModel(
    @DrawableRes val image: Int,
    val title: String,
    val description: String,
) {
    data object FirstPage : OnboardingModel(
        image = R.drawable.splash_1,
        title = "Temukan Koleksi Terbaik",
        description = "Dari motif klasik hingga modern, kami hadirkan batik berkualitas sesuai dengan selera Anda."
    )

    data object SecondPage : OnboardingModel(
        image = R.drawable.splash_2,
        title = "Batik Unik dan Otentik",
        description = "Pilihan batik unik, eksklusif, dan berkelas yang cocok untuk segala kesempatan. "
    )
}