package com.example.batikan.presentation.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.presentation.viewmodel.AddOrderState
import com.example.batikan.presentation.viewmodel.UserState
import com.example.batikan.presentation.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun PaymentScreen(
    url: String,
    context: Context,
    backHandler: () -> Unit,
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel(),
) {
    val addOrderState by viewModel.addOrderState.collectAsState()
    val profileState by viewModel.userState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile()
    }

    val user = when (val state = profileState) {
        is UserState.Success -> state.data
        else -> return
    }

    val webView = remember {
        android.webkit.WebView(context).apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: android.webkit.WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    // Deteksi URL callback yang menunjukkan transaksi berhasil
                    if (url != null && url.contains("status_code=200")) {
                        // Sesuaikan parameter dengan callback sukses dari Midtrans
                         // Kembali ke halaman utama
                        coroutineScope.launch {
                            viewModel.makeOrder(
                                name = user.name,
                                phone = user.phone,
                                address = "Jl. Colombo"
                            )
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

    // Handle AddOrderState
    LaunchedEffect(addOrderState) {
        when (addOrderState) {
            is AddOrderState.Success -> {
                // Navigate to home screen after successful order
                navController.navigate("home_screen") {
                    popUpTo("payment") {
                        inclusive = true
                    }
                }
                Log.e("PaymentScreen", "Order success: ${(addOrderState as AddOrderState.Success).data}")

            }
            is AddOrderState.Error -> {
                Log.e("PaymentScreen", "Order failed: ${(addOrderState as AddOrderState.Error).message}")
            }

            AddOrderState.Idle -> {
                // Initial state, do nothing
            }
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
