package com.example.batikan.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.TextMdRegular
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallMedium
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.ui.theme.TextSmallSemiBold


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingContent(
    modifier: Modifier,
    shippingItems: List<Shipping>,
    navController: NavController


){
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pembayaran",
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
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(shippingItems) { shippingItem ->
                ShippingCard(
                    shippingItem = shippingItem
                )
            }
        }


    }
}

data class Shipping(
    val ImageResource: Int,
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
            Image(
                painter = painterResource(id = shippingItem.ImageResource),
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