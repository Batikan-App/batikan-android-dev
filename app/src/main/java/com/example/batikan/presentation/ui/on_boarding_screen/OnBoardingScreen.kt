package com.example.batikan.presentation.ui.on_boarding_screen

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

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {

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
                    OnboardingGraphUI(onboardingModel = pages[index])
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
            // Text Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    text = "Temukan Koleksi Terbaik",
                    fontSize = 24.sp,
                    style = PlayfairDisplayXsSemiBold,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Dari motif klasik hingga modern, kami hadirkan batik berkualitas sesuai dengan selera Anda.",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

//             Dot Section
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
                Column {
                    ButtonUi(text = buttonState.value[1]) {
                        scope.launch {
                            if(pagerState.currentPage < pages.size - 1) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            } else {
                                onFinished()
                            }
                        }
                    }
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen {

    }
}