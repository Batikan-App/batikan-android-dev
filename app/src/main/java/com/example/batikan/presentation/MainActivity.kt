package com.example.batikan.presentation

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.SyncStateContract.Helpers
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.batikan.R
import com.example.batikan.data.datasource.local.DataStoreManager
import com.example.batikan.presentation.ui.composables.Product
import com.example.batikan.presentation.ui.helpers.resizeImageFile
import com.example.batikan.presentation.ui.screens.BatikanWelcomeScreen
import com.example.batikan.presentation.ui.screens.CameraScreen
import com.example.batikan.presentation.ui.screens.HomeScreenContent
import com.example.batikan.presentation.ui.screens.LoginScreen
import com.example.batikan.presentation.ui.screens.LogoAnimationScreenContent
import com.example.batikan.presentation.ui.screens.PhotoResultScreen
import com.example.batikan.presentation.ui.screens.PhotoResultScreen
import com.example.batikan.presentation.ui.screens.LogoAnimationScreenContent
import com.example.batikan.presentation.ui.screens.ProfileContent
import com.example.batikan.presentation.ui.screens.RegisterScreen
import com.example.batikan.presentation.ui.screens.ScanResultContent
import com.example.batikan.presentation.ui.screens.RegisterScreen
import com.example.batikan.presentation.ui.screens.Shipping
import com.example.batikan.presentation.ui.screens.TokoContent
import com.example.batikan.presentation.ui.screens.TrackingContent
import com.example.batikan.presentation.ui.screens.UpdateProfileContent
import com.example.batikan.presentation.ui.screens.on_boarding_screen.OnboardingScreen
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.viewmodel.BatikViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            BatikanTheme {
                val navController = rememberNavController()
                val tokenState = remember { mutableStateOf<String?>(null) }

                // Ambil token saat komponen pertama kali dibuat
                LaunchedEffect(Unit) {
                    val token = dataStoreManager.getToken()
                    val tokenExp = dataStoreManager.getTokenExp()
                    val currentTime = System.currentTimeMillis()

                    if (token.isNotEmpty() && tokenExp != null && currentTime < tokenExp) {
                        tokenState.value = token
                    } else {
                        tokenState.value = null // Token tidak valid
                        dataStoreManager.deleteToken()
                    }
                }

                // Tentukan startDestination berdasarkan token
                val startDestination = if (tokenState.value.isNullOrEmpty()) {
                    "logo_screen"
                } else {
                    "home_screen"
                }

                NavHost(navController = navController,
                    startDestination = startDestination
                ) {
                    composable(route = "logo_screen"){
                        LogoAnimationScreenContent( navController =  navController)
                    }

                    composable(route = "onboarding_screen") {
                        OnboardingScreen(
                            navController = navController,
                            onFinished = {
                                navController.navigate("welcome_screen") {
                                    popUpTo("onboarding_screen")
                                }
                            }
                        )
                    }

                    composable(route = "welcome_screen") {
                        BatikanWelcomeScreen(navController)
                    }

                    composable(route = "login_screen") {
                        LoginScreen(navController)
                    }

                    composable(route = "register_screen") {
                        RegisterScreen(navController)
                    }

                    composable(route = "home_screen") {
                        HomeScreenContent(
                            navController
                        )
                    }

                    composable(
                        route = "detail_product_screen?batikId={batikId}",
                        arguments = listOf(navArgument("batikId") { type = NavType.StringType})
                    ) { backStackEntry ->
                        val batikId = backStackEntry.arguments?.getString("batikId")
                    }

                    composable(route = "camera_screen") {
                        CameraScreen(navController)
                    }

                    composable(
                        route = "photo_result_screen?photoUri={photoUri}",
                        arguments = listOf(navArgument("photoUri") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val photoUri = backStackEntry.arguments?.getString("photoUri")
                        PhotoResultScreen(
                            navController = navController,
                            photoUri = photoUri,
                            onProceed = { file ->
                                val encodedUri = Uri.encode(file.toURI().toString()) // Encode URI
                                navController.navigate("scan_result_screen?photoUri=$encodedUri")
                            }
                        )
                    }

                    composable(route = "scan_result_screen?photoUri={photoUri}") { backStackEntry ->
                        val photoUri = backStackEntry.arguments?.getString("photoUri")
                        // Ambil ViewModel
                        val batikViewModel: BatikViewModel = hiltViewModel()

                        // Panggil scanBatik ketika layar ini dibuka
                        LaunchedEffect(photoUri) {
                            photoUri?.let {
                                val imageFile = File(Uri.parse(it).path ?: "")
                                val resizedimageFile = resizeImageFile(imageFile)

                                batikViewModel.scanBatik(resizedimageFile)
                            }
                        }

                        // Ambil uiState untuk disampaikan ke ScanResultContent
                        val uiState = batikViewModel.scanResultState.collectAsState().value

                        ScanResultContent(
                            photoUri = photoUri,
                            uiState = uiState,
                            navController = navController,
                        )
                    }
//                BatikScanCard(modifier = Modifier.padding(start = 30.dp), navController = )

                    composable(route = "toko_screen") {
                        TokoContent(
                            navController,
//                            featuredProducts = listOf(
//                                Product(R.drawable.batik_new, "Batik A", "$20"),
//                                Product(R.drawable.batik_new, "Batik B", "$25"),
//                                Product(R.drawable.batik_new, "Batik C", "$30"),
//                                Product(R.drawable.batik_new, "Batik D", "$35")
//                            ),
//                            originProduct = listOf(
//                                Product(R.drawable.batik_new, "Batik A", "$20"),
//                                Product(R.drawable.batik_new, "Batik B", "$25"),
//                                Product(R.drawable.batik_new, "Batik C", "$30"),
//                                Product(R.drawable.batik_new, "Batik D", "$35")
//                            )
                        )
                    }

                    composable(route = "profile_screen") {
                        ProfileContent(
                            navController
                        )
                    }

                    composable(route = "update_profile_screen"){
                        UpdateProfileContent(
                            navController = navController
                        )
                    }

                    composable(route = "tracking_screen"){
                        TrackingContent(
                            navController = navController,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}