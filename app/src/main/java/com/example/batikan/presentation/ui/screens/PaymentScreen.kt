package com.example.batikan.presentation.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PaymentScreen(
    url: String,
    context: Context,
    backHandler: () -> Unit,
    navController: NavController
) {
    val webView = remember {
        android.webkit.WebView(context).apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: android.webkit.WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    // Deteksi URL callback yang menunjukkan transaksi berhasil
                    if (url != null && url.contains("status_code=200")) {
                        // Sesuaikan parameter dengan callback sukses dari Midtrans
                         // Kembali ke halaman utama
                        navController.navigate("home_screen") {
                            popUpTo("payment") {
                                inclusive = true
                            }
                        }
                    }
                }
            }
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
        }
    }

    BackHandler(onBack = backHandler)

    LaunchedEffect(url) {
        if (url.isNotEmpty()) {
            webView.loadUrl(url)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        AndroidView(
            factory = { webView },
            modifier = Modifier.fillMaxSize()
        )
    }
}
