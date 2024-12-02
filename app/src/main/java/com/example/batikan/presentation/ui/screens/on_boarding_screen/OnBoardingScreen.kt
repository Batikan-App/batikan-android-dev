package com.example.batikan.presentation.ui.screens.on_boarding_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.batikan.presentation.ui.composables.ButtonUi
import com.example.batikan.presentation.ui.composables.IndicatorUI
import com.example.batikan.presentation.ui.theme.PlayfairDisplayXsSemiBold
import com.example.batikan.presentation.ui.theme.Primary600
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallRegular

@Composable
fun OnboardingGraphUI(
    navController: NavController,
    onboardingModel: OnboardingModel
) {
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
                    .clickable {
                        navController.navigate("welcome_screen") {
                            popUpTo("onboarding_screen") { inclusive = true }
                        }
                    }
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
                painter = painterResource(id = onboardingModel.image), // Menggunakan gambar dari model
                contentDescription = "Model Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}


@Composable
fun OnboardingScreen(
    navController: NavController,
    onFinished: () -> Unit
) {

    val pages = listOf(
        OnboardingModel.FirstPage, OnboardingModel.SecondPage
    )

    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }
    val buttonState = remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> listOf("", "Selanjutnya")
                1 -> listOf("Sebelumnya", "Selanjutnya")
                2 -> listOf("Sebelumnya", "Selanjutnya")
                else -> listOf("", "")
            }
        }
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        content = {
            Column(Modifier.padding(it)) {
                HorizontalPager(state = pagerState) { index ->
                    // Kirim data halaman yang sesuai
                    OnboardingGraphUI(
                        navController = navController,
                        onboardingModel = pages[index]
                    )
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .height(290.dp)
                    .fillMaxSize()
                    .padding(10.dp, 10.dp),
            ) {
                // Text Section (menggunakan title dan description dari onboardingModel)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text(
                        text = pages[pagerState.currentPage].title, // Menggunakan title dari model
                        fontSize = 24.sp,
                        style = PlayfairDisplayXsSemiBold,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = pages[pagerState.currentPage].description, // Menggunakan description dari model
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                }

                // Dot Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center) {
                        IndicatorUI(pageSize = pages.size, currentPage = pagerState.currentPage)
                    }
                }

                // Button Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Tombol Selanjutnya atau Selesai
                    ButtonUi(
                        text = "Selanjutnya",
                    ) {
                        scope.launch {
                            if (pagerState.currentPage < pages.size - 1) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            } else {
                                onFinished()
                            }
                        }
                    }

                    if (pagerState.currentPage > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Tombol Sebelumnya
                    if (pagerState.currentPage > 0) { // Tampilkan hanya jika bukan halaman pertama
                        ButtonUi(text = "Sebelumnya", isNext = false) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    }

                }
            }
        }
    )
}


