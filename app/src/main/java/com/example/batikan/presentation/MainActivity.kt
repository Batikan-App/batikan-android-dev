package com.example.batikan.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.batikan.R
import com.example.batikan.data.local.DataStoreManager
import com.example.batikan.presentation.ui.composables.Product
import com.example.batikan.presentation.ui.screens.CameraScreen
import com.example.batikan.presentation.ui.screens.HomeScreenContent
import com.example.batikan.presentation.ui.screens.LoginScreen
import com.example.batikan.presentation.ui.screens.ProfileContent
import com.example.batikan.presentation.ui.screens.RegisterScreen
import com.example.batikan.presentation.ui.screens.ScanResult
import com.example.batikan.presentation.ui.screens.ScanResultContent
import com.example.batikan.presentation.ui.screens.Shipping
import com.example.batikan.presentation.ui.screens.TokoContent
import com.example.batikan.presentation.ui.screens.TrackingContent
import com.example.batikan.presentation.ui.screens.UpdateProfileContent
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.DisplayLgBold
import com.example.batikan.presentation.ui.theme.Primary600
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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

                    composable(route = "register_screen") {
                        RegisterScreen(navController)
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

                    composable(route = "scan_result_screen?photoUri={photoUri}") { backStackEntry ->
                        val photoUri = backStackEntry.arguments?.getString("photoUri")
                        ScanResultContent(
                            photoUri = photoUri,
                            result = ScanResult(
                                name = "Batik Papua",
                                aboutMotif = "Batik Papua adalah salah satu jenis batik khas Indonesia yang berasal dari daerah Papua. Berbeda dengan batik dari daerah lain, batik Papua memiliki ciri khas pada motif dan warna yang menggambarkan budaya, alam, serta kehidupan masyarakat Papua. Motif-motif pada batik Papua seringkali terinspirasi dari bentuk-bentuk alami seperti tumbuhan, hewan khas Papua, dan simbol adat yang memiliki makna mendalam.",
                                origin = "Papua Barat"
                            ),
                            similiarProduct = listOf(
                                Product(R.drawable.batik_new, "Batik A", "$20"),
                                Product(R.drawable.batik_new, "Batik B", "$25"),
                                Product(R.drawable.batik_new, "Batik C", "$30"),
                                Product(R.drawable.batik_new, "Batik D", "$35")
                            ),
                            navController = navController
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