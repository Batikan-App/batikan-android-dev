package com.example.batikan.presentation.ui.on_boarding_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.TextSmallRegular

@Composable
fun OnboardingGraphUI(onboardingModel: OnboardingModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF5C0A3B), Color(0xFFA81854))
                )
            )
    ) {
        // Header Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.batikan),
                contentDescription = "Batikan Logo",
                modifier = Modifier
                    .size(150.dp)
            )
            Text(
                text = "Lewati semua",
                fontWeight = FontWeight.Bold,
                style = TextSmallRegular,
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(top = 20.dp)
            )
        }

        // Image Section
        Column(
            modifier = Modifier
                .padding(top = 160.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.image1), // Ganti dengan gambar Anda
                contentDescription = "Model Image",
                modifier = Modifier
                    .size(width = 280.dp, height = 400.dp)
                    .padding(top = 20.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    OnboardingGraphUI(onboardingModel = OnboardingModel.FirstPage)
}