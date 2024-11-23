package com.example.batikan.presentation.ui.photo_result_screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import java.io.File

@Composable
fun PhotoResultScreen(navController: NavController, photoUri: String?) {
    val uri = photoUri?.toUri()

    Box(modifier = Modifier.fillMaxSize()) {
        uri?.let {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .build()
                ),
                contentDescription = "Hasil Foto",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(300.dp)
            )
        }

        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Kembali ke Kamera")
        }
    }
}


