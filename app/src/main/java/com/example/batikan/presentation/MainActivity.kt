package com.example.batikan.presentation

import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.batikan.R
import com.example.batikan.data.datasource.local.DataStoreManager
import com.example.batikan.presentation.ui.composables.Product
import com.example.batikan.presentation.ui.screens.CameraScreen
import com.example.batikan.presentation.ui.screens.HomeScreenContent
import com.example.batikan.presentation.ui.screens.LoginScreen
import com.example.batikan.presentation.ui.screens.PhotoResultScreen
import com.example.batikan.presentation.ui.screens.ProfileContent
import com.example.batikan.presentation.ui.screens.ScanResultContent
import com.example.batikan.presentation.ui.screens.Shipping
import com.example.batikan.presentation.ui.screens.TokoContent
import com.example.batikan.presentation.ui.screens.TrackingContent
import com.example.batikan.presentation.ui.screens.UpdateProfileContent
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.DisplayLgBold
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.viewmodel.BatikViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BatikanTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login_screen") {

                    composable(route = "login_screen") {
                        LoginScreen(navController)
                    }

                    composable(route = "home_screen") {
                        HomeScreenContent(
                            navController,
                            userName = "John Doe",
                        )
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
                                batikViewModel.scanBatik(imageFile)
                            }
                        }

                        // Ambil uiState untuk disampaikan ke ScanResultContent
                        val uiState = batikViewModel.scanResultState.collectAsState().value

                        ScanResultContent(
                            photoUri = photoUri,
                            uiState = uiState,
                            similiarProduct = listOf(
                                Product(R.drawable.batik_new, "Batik A", "$20"),
                                Product(R.drawable.batik_new, "Batik B", "$25"),
                                Product(R.drawable.batik_new, "Batik C", "$30"),
                                Product(R.drawable.batik_new, "Batik D", "$35")
                            ),
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
                            navController = navController,
                            name = "John Doe",
                            email = "johnn@batikan.com",
                            phoneNumber = "081234567890"
                        )
                    }

                    composable(route = "tracking_screen"){
                        TrackingContent(
                            navController = navController,
                            modifier = Modifier,
                            shippingItems = listOf(
                                Shipping(
                                    ImageResource = R.drawable.batik_new,
                                    title = "Batik Pekalongan",
                                    status = "Dikirim",
                                    number = "1234567890"
                                ),
                                Shipping(
                                    ImageResource = R.drawable.batik_new,
                                    title = "Batik Papua",
                                    status = "Sampai",
                                    number = "23133213232133"
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}