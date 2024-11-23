package com.example.batikan.presentation.ui.home_screen

import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.Black
import com.example.batikan.presentation.ui.theme.DisplayLgBold
import com.example.batikan.presentation.ui.theme.DisplayXsRegular
import com.example.batikan.presentation.ui.theme.DisplayXsSemiBold
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.White

@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Cari batik cepat lewat scan",
            style = DisplayLgBold,
            color = Black,
        )

        Text(
            text = "Scan batikmu sekarang",
            style = DisplayXsRegular,
            color = Black
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(
                color = Primary600
            )
            .clickable(onClick = {
                navController.navigate("camera_screen")
            })
        ) {
            Text(
                text = "Icon",
                color = White
            )
            Column {
                Text(
                    text = "Scan Batikmu",
                    style = DisplayXsSemiBold,
                    color = White
                )

                Text(
                    text = "Cukup arahkan ke kamera dan kami akan bantu nyari batiknya",
                    style = DisplayXsRegular,
                    color = White
                )
            }
        }
    }
}