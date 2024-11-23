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
import com.example.batikan.presentation.ui.camera_screen.CameraScreen
import com.example.batikan.presentation.ui.home_screen.HomeScreen
import com.example.batikan.presentation.ui.photo_result_screen.PhotoResultScreen
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.DisplayLgBold
import com.example.batikan.presentation.ui.theme.Primary600

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            BatikanTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home_screen") {
                    composable(route = "home_screen") {
                        HomeScreen(navController)
                    }
                    composable(route = "camera_screen") {
                        CameraScreen(navController)
                    }
                    composable(route = "photo_result_screen?photoUri={photoUri}") { backStackEntry ->
                        val photoUri = backStackEntry.arguments?.getString("photoUri")
                        PhotoResultScreen(navController, photoUri = photoUri)
                    }
                }
            }
        }
    }
}