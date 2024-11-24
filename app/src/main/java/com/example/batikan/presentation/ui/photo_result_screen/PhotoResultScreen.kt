package com.example.batikan.presentation.ui.photo_result_screen

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.batikan.presentation.ui.theme.DisplayXsSemiBold
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.White
import java.io.File

@Composable
fun PhotoResultScreen(navController: NavController, photoUri: String?) {
    val uri = photoUri?.toUri()

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
                    .size(600.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = { navController.navigateUp() },
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
                onClick = { navController.navigate("camera_screen") },
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

@Preview(showBackground = true)
@Composable
fun PreviewPhotoResultScreen() {
    PhotoResultScreen(navController = rememberNavController(), photoUri = "https://images.unsplash.com/photo-1672716912554-c23ba8fac4ce?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YmF0aWt8ZW58MHx8MHx8fDA%3D")
}


