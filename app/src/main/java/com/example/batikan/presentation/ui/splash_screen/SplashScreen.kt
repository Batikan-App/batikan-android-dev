package com.example.batikan.presentation.ui.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.BatikanTheme


@Composable
fun SplashScreen() {
//        LaunchedEffect(key1 = true, block = {
//            delay(2000)
//            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
//        })
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(
                id = R.drawable.splashscreen
            ),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSplashScreen() {
    BatikanTheme {
        SplashScreen()
    }
}