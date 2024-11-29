package com.example.batikan.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.composables.BatikScanCard
import com.example.batikan.presentation.ui.composables.GreetingSection
import com.example.batikan.presentation.ui.composables.NewProductCard
import com.example.batikan.presentation.ui.composables.Product
import com.example.batikan.presentation.ui.composables.ProductCardList
import com.example.batikan.presentation.ui.composables.SectionTitle
import com.example.batikan.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    userName: String
) {
    val batikList by viewModel.batikList.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchBatikList()
    }

    if (isLoading) {
        // TODO
    } else {
        HomeScreenContent(
            navController = navController,
            userName = userName,
            products = batikList.map {
                Product(
                    imageResource = R.drawable.batik_new,
                    title = it.data.name,
                    price = "${it.data.price}"
                )
            }
        )
    }
}

@Composable
fun HomeScreenContent(
    navController: NavController,
    userName: String,
    products: List<Product>
){


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {}
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                GreetingSection(
                    userName = userName,
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }

            item {
                NewProductCard()
            }

            item {
                SectionTitle(
                    onActionClick = {},
                    title = "Tahu motifnya tapi susah nyari?",
                    description = "Scan aja motif batiknya!",
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
                Spacer(Modifier.height(8.dp))
                BatikScanCard(modifier = Modifier.padding(horizontal = 30.dp), navController = navController)

            }

            item {
                SectionTitle(
                    title = "Produk keren hari ini",
                    description = "Tentukan pilihan batikmu!",
                    actionText = "Lihat semua",
                    onActionClick = { navController.navigate("toko_screen") },
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
                Spacer(Modifier.height(8.dp))
                ProductCardList(productList = products)
            }

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    BatikanTheme {
//        HomeScreenContent(
//            navController = ,
//            userName = "John Doe",
//            products = listOf(
//                Product(R.drawable.batik_new, "Batik A", "$20"),
//                Product(R.drawable.batik_new, "Batik B", "$25"),
//                Product(R.drawable.batik_new, "Batik C", "$30"),
//                Product(R.drawable.batik_new, "Batik D", "$35")
//            )
//        )
//    }
//}