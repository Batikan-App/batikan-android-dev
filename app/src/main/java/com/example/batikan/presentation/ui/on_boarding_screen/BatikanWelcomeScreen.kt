package com.example.batikan.presentation.ui.on_boarding_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.DisplayXsRegular
import com.example.batikan.presentation.ui.theme.Primary600

@Composable
fun BatikanWelcomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_batik),
            contentDescription = "Background Batikan",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top=420.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.batikan),
                contentDescription = "Logo Batikan",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 214.dp, height = 60.dp)
            )

            Text(
                text = "Selamat Datang",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.padding(top = 34.dp)
            )

            Text(
                text = "Bergabung bersama kami dan miliki\n" +
                "kebanggaan budaya di setiap helai kain",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {/*TODO: Handle login */},
                    colors = ButtonDefaults.buttonColors(containerColor = Primary600),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(bottom = 8.dp),
                    shape = RectangleShape
                ) {
                    Text(
                        text = "Masuk kembali",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                OutlinedButton(
                    onClick = {/*TODO: implement handle buat akun */},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(top = 8.dp),
                    shape = RectangleShape
                ) {
                    Text(
                        text = "Baru di sini?, Buat akun sekarang",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable

private fun PreviewLastOnBoardingScreen() {
    BatikanTheme {
        BatikanWelcomeScreen()
    }
}