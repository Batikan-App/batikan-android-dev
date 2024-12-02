package com.example.batikan.presentation.ui.navigation;

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    object OnBoardingScreen: Route(route = "onBoardingScreen")

    object HomeScreen: Route(route = "homeScreen")

    object CameraScreen: Route(route = "cameraScreen")

    object DetailProductScreen: Route(route = "detailProductScreen")

    object LoginScreen: Route(route = "loginScreen")

    object RegisterScreen: Route(route = "registerScreen")

    object CartScreen: Route(route = "cartScreen")

    object PaymentDetailScreen: Route(route = "paymentDetailScreen")

    object ScanResultScreen: Route(route = "scanResultScreen")

    object TokoScreen: Route(route = "tokoScreen")

    object WelcomeScreen: Route(route = "welcomeSCreen")

    object ProfileScreen: Route(route = "profileScreen")

    object UpdateProfileScreen: Route(route = "updateProfileScreen")

    object TrackingScreen: Route(route = "trackingScreen")

    object LogoAnimationScreen: Route(route = "logoScreen")
}
