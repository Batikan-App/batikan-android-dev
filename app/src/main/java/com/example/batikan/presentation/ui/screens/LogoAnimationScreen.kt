package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.batikan.R

@Composable
fun LogoAnimationScreenContent(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.logo)
        )
        var isPlaying by remember { mutableStateOf(true) }
        val progress by animateLottieCompositionAsState(
            composition = composition,
            isPlaying = isPlaying
        )

        LaunchedEffect(progress) {
            if (progress == 1f) {
                isPlaying = false
                navController.navigate("onboarding_screen") {
                    popUpTo("splash_screen") { inclusive = true }
                }
            }
        }

        LottieAnimation(
            composition = composition,
            modifier = Modifier.fillMaxSize(),
            progress = { progress }
        )
    }
}
