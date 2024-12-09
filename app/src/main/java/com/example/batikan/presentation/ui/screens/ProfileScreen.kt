package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.composables.BottomNavBar
import com.example.batikan.presentation.ui.composables.ButtonWithIcon
import com.example.batikan.presentation.ui.composables.ProfileCard
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallMedium
import com.example.batikan.presentation.ui.theme.TextXsRegular
import com.example.batikan.presentation.viewmodel.AuthViewModel
import com.example.batikan.presentation.viewmodel.LoginState
import com.example.batikan.presentation.viewmodel.LogoutState
import com.example.batikan.presentation.viewmodel.UserState
import com.example.batikan.presentation.viewmodel.UserViewModel


@Composable
fun ProfileContent(
    navController: NavController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
    ) {
    val logoutState by authViewModel.logoutState.collectAsState()
    val profileState by userViewModel.userState.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.fetchUserProfile() // Fungsi dipanggil di sini
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 30.dp)
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            item {
                when (profileState) {
                    is UserState.Loading -> {
                        ProfileCard(
                            imageResource = R.drawable.logo_batikan,
                            name = "",
                            email = "",
                            phoneNumber = "",
                            onActionClick = { },
                            isLoading = true
                        )
                    }
                    is UserState.Success -> {
                        val user = (profileState as UserState.Success).data
                        ProfileCard(
                            imageResource = R.drawable.logo_batikan,
                            name = user.name,
                            email = user.email,
                            phoneNumber = user.phone,
                            onActionClick = { navController.navigate("update_profile_screen") },
                            isLoading = false
                        )
                    }
                    is UserState.Error -> {
                        // Menampilkan pesan error
                        Text(
                            text = (profileState as UserState.Error).message,
                            color = Color.Red,
                            style = TextMdSemiBold
                        )
                    }
                    else -> {
                        Text(
                            text = "Data profil belum dimuat",
                            style = TextMdSemiBold
                        )
                    }
                }
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
                    onClick = {
                        authViewModel.logout()
                    },
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
                when (logoutState) {
                    is LogoutState.Loading -> CircularProgressIndicator()
                    is LogoutState.Success -> {
                        LaunchedEffect(Unit) {
                            // Navigate to welcome screen
                            navController.navigate("welcome_screen")
                        }
                    }
                    is LogoutState.Error -> Text((logoutState as LoginState.Error).message, color = Color.Red)
                    else -> {}
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