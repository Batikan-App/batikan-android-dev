package com.example.batikan.presentation.ui.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.batikan.presentation.ui.theme.DisplayXsSemiBold
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.White
import java.io.File
import java.net.URLDecoder

@Composable
fun PhotoResultScreen(
    navController: NavController,
    photoUri: String?,
    onProceed: (File) -> Unit,
) {
    val context = LocalContext.current
    val decodedUri = photoUri?.let { Uri.parse(URLDecoder.decode(it, "UTF-8")) }
    val photoFile = decodedUri?.let { File(it.path ?: "") }

    Row(
        modifier = Modifier
            .padding(top = 96.dp, start = 16.dp)
    ) {
        Text(
            text = "Telusuri Motif Batik",
            style = DisplayXsSemiBold
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        photoFile?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Hasil Foto",
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    photoFile?.let(onProceed)
                },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .size(width = 400.dp, height = 50.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary600, contentColor = White
                )
            ) {
                Text("Lanjutkan")
            }
            OutlinedButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .size(width = 400.dp, height = 50.dp),
                shape = RectangleShape,
                border = BorderStroke(1.dp, Primary600),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary600),
            ) {
                Text("Foto Ulang")
            }
        }
    }
}
