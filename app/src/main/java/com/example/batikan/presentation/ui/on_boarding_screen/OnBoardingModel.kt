package com.example.batikan.presentation.ui.on_boarding_screen

import androidx.annotation.DrawableRes
import com.example.batikan.R


sealed class OnboardingModel(
    @DrawableRes val image: Int,
    val title: String,
    val description: String,
) {
    data object FirstPage : OnboardingModel(
        image = R.drawable.image1,
        title = "Your Reading Partner",
        description = "Read as many book as you want, anywhere you want"
    )

    data object SecondPage : OnboardingModel(
        image = R.drawable.image2,
        title = "Your Personal Library",
        description = "Organize books in different ways, make your own library"
    )
}
