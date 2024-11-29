package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.composables.BottomNavBar
import com.example.batikan.presentation.ui.composables.ButtonWithIcon
import com.example.batikan.presentation.ui.composables.ProfileCard
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextMdSemiBold


@Composable
fun ProfileContent(
    navController: NavController,
    modifier: Modifier = Modifier,

    ) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            item {
                ProfileCard(
                    imageResource = R.drawable.batik_new,
                    name = "John doe",
                    email = "test@batikan.com",
                    phoneNumber = "+6281247016022",
                    onActionClick = { navController.navigate("update_profile_screen") }
                )
            }

            item {
                Text(
                    text = "Akun",
                    style = TextMdSemiBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                ButtonWithIcon(
                    onClick = {},
                    text = "Notifikasi",
                    leadingIcon = Icons.Default.Notifications,
                    trailingIocn = Icons.Default.ChevronRight
                )

                ButtonWithIcon(
                    onClick = {},
                    text = "Pengaturan",
                    leadingIcon = Icons.Default.Settings,
                    trailingIocn = Icons.Default.ChevronRight
                )

                ButtonWithIcon(
                    onClick = {},
                    text = "Batik Favorit",
                    leadingIcon = Icons.Default.Bookmark,
                    trailingIocn = Icons.Default.ChevronRight
                )
            }

            item {
                Text(
                    text = "Lainya",
                    style = TextMdSemiBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                ButtonWithIcon(
                    onClick = { navController.navigate("tracking_screen") },
                    text = "Lacak Pengiriman",
                    leadingIcon = Icons.Default.CarCrash,
                    trailingIocn = Icons.Default.ChevronRight
                )

                ButtonWithIcon(
                    onClick = {},
                    text = "Tentang",
                    leadingIcon = Icons.Default.Info,
                    trailingIocn = Icons.Default.ChevronRight
                )

            }

            item {
                Button(
                    onClick = {},
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Keluar",
                        color = Primary600,
                        style = TextMdSemiBold
                    )
                }

            }


        }

    }
}





//@Preview(showBackground = true)
//@Composable
//fun ProfileScreenPreview() {
//    BatikanTheme {
//        ProfileScreenContent()
//    }
//}