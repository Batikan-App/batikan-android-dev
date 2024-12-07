package com.example.batikan.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextMdRegular
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallMedium
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.ui.theme.TextSmallSemiBold
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
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = TextPrimary
                        )
                    }
                }
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
                        order.items.forEach { item ->
                            ShippingCard(
                                shippingItem = Shipping(
                                    ImageResource = item.img.firstOrNull() ?: "",
                                    title = item.name,
                                    status = order.status,
                                    number = order.orderId
                                )
                            )
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

//        LazyColumn (
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding),
//            verticalArrangement = Arrangement.spacedBy(20.dp)
//        ) {
//            items(shippingItems) { shippingItem ->
//                ShippingCard(
//                    shippingItem = shippingItem
//                )
//            }
//        }


    }
}

data class Shipping(
    val ImageResource: String,
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
                model = shippingItem.ImageResource,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp, 60.dp)
            )
            Column {
                Text(
                    text = shippingItem.title,
                    style = TextSmallSemiBold,
                    color = TextPrimary
                )
                Text(
                    text = shippingItem.status,
                    style = TextSmallRegular,
                    color = TextSecondary
                )
            }
        }
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

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    BatikanTheme {
//        TrackingContent(
//            modifier = Modifier,
//            shippingItems = listOf(
//                Shipping(
//                    ImageResource = R.drawable.batik_new,
//                    title = "Batik Pekalongan",
//                    status = "Dikirim",
//                    number = "1234567890"
//                ),
//                Shipping(
//                    ImageResource = R.drawable.batik_new,
//                    title = "Batik Papua",
//                    status = "Sampai",
//                    number = "23133213232133"
//                )
//            )
//        )
//    }
//}