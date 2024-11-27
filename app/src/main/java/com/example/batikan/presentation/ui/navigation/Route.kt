package com.example.batikan.presentation.ui.navigation;

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object OnBoardingScreen: Route(route = "onBoardingScreen")

    object HomeScreen: Route(route = "homeScreen")

    object CameraScreen: Route(route = "cameraScreen")

    object detailProcutScreen: Route(route = "detailProductScreen")

//    TODO("Not yet implemented")
}
