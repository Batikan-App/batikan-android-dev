package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.composables.AboutBatik
import com.example.batikan.presentation.ui.composables.CardScanResult
import com.example.batikan.presentation.ui.composables.PageTitle
import com.example.batikan.presentation.ui.composables.ProductSection
import com.example.batikan.presentation.ui.composables.SectionTitle
import com.example.batikan.presentation.ui.composables.VisualTryOnCard
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.util.shimmerEffect
import com.example.batikan.presentation.viewmodel.BatikViewModel
import com.example.batikan.presentation.viewmodel.ScanResultState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanResultContent(
    photoUri: String?,
    viewModel: BatikViewModel = hiltViewModel(),
    uiState: ScanResultState,
    onProductClick: (String) -> Unit,
    navController: NavController
) {

    val similarBatikProduct by viewModel.productList.collectAsState()
    val batikState by viewModel.batikState.collectAsState()

    when (uiState) {
        is ScanResultState.Idle -> {
            Text("Menunggu proses scan...", modifier = Modifier.fillMaxSize())
        }
        is ScanResultState.Loading -> {
            Column {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .height(200.dp)
                        .shimmerEffect()
                )
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(500.dp)
                        .shimmerEffect()
                )
            }
        }
        is ScanResultState.Success -> {
            val response = uiState.response
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Hasil Scan Batik",
                                style = TextMdSemiBold,
                                color = TextPrimary
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    tint = TextPrimary
                                )
                            }
                        }
                    )
                },
                bottomBar = {}
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    item {
                        PageTitle(
                            title = "Hasil Scan Batik",
                            description = "Hasil scan batikmu",
                            modifier = Modifier.padding(start = 30.dp)
                        )
                    }

                    item {
                        CardScanResult(
                            name = response.data.data.name,
                            origin = response.data.data.origin,
                            imageResource = R.drawable.batik_new,
                            onActionClick = {},
                            modifier = Modifier.padding(start = 30.dp),
                            photoUri = photoUri
                        )
                    }

                    item {
                        AboutBatik(
                            description = response.data.data.desc,
                            modifier = Modifier.padding(horizontal = 30.dp)
                        )
                    }

                    item {
                        SectionTitle(
                            title = "Visual Try-On",
                            description = "Coba batik yang kamu suka secara virtual",
                            modifier = Modifier.padding(start = 30.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        VisualTryOnCard(
                            modifier = Modifier.padding(horizontal = 30.dp)
                        )
                    }

                    item {
                        ProductSection(
                            modifier = Modifier
                                .padding(horizontal = 30.dp, vertical = 10.dp),
                            title = "Produk terkait",
                            description = "Beli batiknya sekarang",
                            productList = similarBatikProduct,
                            isLoading = false,
                            onProductClick = onProductClick
                        )
                    }
                }
            }
        }
        is ScanResultState.Error -> {
            Text(
                text = "Error: ${uiState.message}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}