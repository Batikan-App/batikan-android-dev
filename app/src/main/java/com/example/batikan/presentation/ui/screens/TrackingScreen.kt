package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.batikan.presentation.ui.theme.BorderPrimary
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallMedium
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.ui.theme.White
import com.example.batikan.presentation.viewmodel.OrderState
import com.example.batikan.presentation.viewmodel.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingContent(
    modifier: Modifier,
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel()
) {
    val orderState by viewModel.orderState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUserOrders()
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tracking order",
                        style = TextMdSemiBold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = TextPrimary
                        )
                    }
                },
                // Mengatur warna latar belakang menjadi putih
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    navigationIconContentColor = TextPrimary,
                    titleContentColor = TextPrimary
                )
            )
        },
    ) { innerPadding ->
        when (orderState) {
            is OrderState.Idle -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Menunggu data...",
                        style = TextSmallRegular,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }

            is OrderState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CircularProgressIndicator()
                }
            }

            is OrderState.Empty -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.HideImage,
                            contentDescription = "Empty Orders",
                            tint = TextSecondary,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Belum ada pesanan.",
                            style = TextSmallRegular,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            is OrderState.Success -> {
                val orders = (orderState as OrderState.Success).orders ?: emptyList()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(orders) { order ->
                        order.items.forEachIndexed { index, item ->
                            ShippingCard(
                                shippingItem = Shipping(
                                    imageResource = item.img.firstOrNull() ?: "",
                                    title = item.name,
                                    status = order.status,
                                    number = order.orderId
                                )
                            )
                            // Tambahkan Divider kecuali untuk elemen terakhir
                            if (index < order.items.size - 1) {
                                HorizontalDivider(
                                    color = BorderPrimary,
                                    thickness = 8.dp,
                                    modifier = Modifier.padding(vertical = 16.dp)
                                )
                            }
                        }
                    }
                }
            }

            is OrderState.Error -> {
                val errorMessage = (orderState as OrderState.Error).message
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: $errorMessage",
                            style = TextSmallRegular,
                            color = Primary600,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.getUserOrders() },
                            colors = ButtonDefaults.buttonColors(containerColor = Primary600)
                        ) {
                            Text(
                                text = "Coba Lagi",
                                style = TextSmallRegular,
                                color = White
                            )
                        }
                    }
                }
            }
        }
    }
}

data class Shipping(
    val imageResource: String,
    val title: String,
    val status: String,
    val number: String
)

@Composable
fun ShippingCard(
    modifier: Modifier = Modifier,
    shippingItem: Shipping

){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,

    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = shippingItem.imageResource,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp, 80.dp)
            )
            Column (


            ) {
                Text(
                    text = shippingItem.title,
                    style = TextMdSemiBold,
                    color = TextPrimary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = shippingItem.status,
                    style = TextSmallRegular,
                    color = TextSecondary
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = shippingItem.number,
                        style = TextSmallMedium,
                        color = TextPrimary
                    )
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.CopyAll,
                            contentDescription = null,
                            tint = TextPrimary
                        )
                    }
                }
            }
        }
    }
}