package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.TextLgSemiBold
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.ui.theme.TextXsRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    navController: NavController,
    ) {
    val appDescription = """
        Batikan adalah aplikasi e-commerce berbasis AI yang dirancang untuk melestarikan dan mempromosikan warisan budaya Indonesia melalui teknologi modern. 
        Aplikasi ini memanfaatkan pengenalan gambar untuk mengautentikasi batik asli dan menyediakan marketplace khusus untuk produk batik autentik.
        Dengan Batikan, Anda dapat menjelajahi keindahan batik Indonesia, memastikan keaslian produk, dan mendukung para pengrajin lokal.
    """.trimIndent()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tentang Batikan",
                        style = TextMdSemiBold,
                        color = TextPrimary
                    )
                   },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("profile_screen") {
                            popUpTo("about_app_screen") {
                                inclusive = true
                            }
                        }

                     }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo_batikan), // Tambahkan logo Batikan ke resource drawable
                contentDescription = "Logo Batikan",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nama Aplikasi
            Text(
                text = "Batikan",
                style = TextLgSemiBold,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Deskripsi Aplikasi
            Text(
                text = appDescription,
                style = TextSmallRegular,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )


            Spacer(modifier = Modifier.height(24.dp))

            // Informasi Tambahan
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Versi: 1.0.0", style = TextXsRegular)
                Text(text = "Hak Cipta © 2024 Batikan", style = TextXsRegular)
            }
        }
    }
}
