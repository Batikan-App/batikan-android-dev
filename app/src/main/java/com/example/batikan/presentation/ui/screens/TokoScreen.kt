package com.example.batikan.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.presentation.ui.composables.BatikScanCard
import com.example.batikan.presentation.ui.composables.BottomNavBar
import com.example.batikan.presentation.ui.composables.FilterChipGroup
import com.example.batikan.presentation.ui.composables.PageTitle
import com.example.batikan.presentation.ui.composables.ProductOriginList
import com.example.batikan.presentation.ui.composables.ProductSection
import com.example.batikan.presentation.ui.composables.SearchBar
import com.example.batikan.presentation.ui.composables.SectionTitle
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.viewmodel.BatikOriginState
import com.example.batikan.presentation.viewmodel.BatikState
import com.example.batikan.presentation.viewmodel.BatikViewModel

@Composable
fun TokoContent(
    navController: NavController,
    onProductClick: (String) -> Unit,
    viewModel: BatikViewModel = hiltViewModel()
){
    var query by remember { mutableStateOf("") }
    val batikState by viewModel.batikState.collectAsState()
    val originState by viewModel.batikOriginState.collectAsState()
    val productOriginList by viewModel.productOriginList.collectAsState()
    var selectedOrigin by remember { mutableStateOf("") }
    val productList by viewModel.productList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchBatik()
    }

    Log.d("TokoScreen", "Product List Size: ${productList.size}")
    productList.forEach { product->
        Log.d("TokoScreen", "Product: $product")
    }


    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {
            item {
                Row {
                    PageTitle(
                        title = "Koleksi Batik Kami",
                        description = "Temukan batik kualitas terbaik dari kami",
                        modifier = Modifier.padding(horizontal = 30.dp),
                    )

                    IconButton(
                        onClick = {
                            navController.navigate("cart_screen")
//                                popUpTo("detail_product_screen") {
//                                    inclusive = true
//                                }
                        }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = TextPrimary
                        )
                    }
                }
            }

            item {
                SearchBar(
                    query = query,
                    onQueryChanged = { query = it },
                    onSearch = {
                        viewModel.searchBatik(query)
                        navController.navigate("search_result_screen/$query")
                    },
                )
            }

            item {
                when (batikState) {
                    is BatikState.Loading -> {
                        ProductSection(
                            title = "Produk keren hari ini",
                            description = "Tentukan pilihan batikmu!",
                            productList = productList,
                            onProductClick = onProductClick,
                            isLoading = true,
                            modifier = Modifier.padding(horizontal = 30.dp),
                        )
                    }

                    is BatikState.Success -> {
                        ProductSection(
                            title = "Produk keren hari ini",
                            description = "Tentukan pilihan batikmu!",
                            productList = productList,
                            onProductClick = onProductClick,
                            isLoading = false,
                            modifier = Modifier.padding(horizontal = 30.dp),
                        )
                    }

                    is BatikState.Error -> {
                        Text(
                            text = (batikState as BatikState.Error).message,
                            color = Color.Red,
                            style = TextMdSemiBold
                        )
                    }

                    else -> {
                        Text(
                            text = "Produk segera tersedia 😉",
                            color = Color.Red,
                            style = TextMdSemiBold,
                            modifier = Modifier.padding(horizontal = 30.dp)
                        )
                    }
                }

            }

            item {
                SectionTitle(
                    title = "Cari batik cepat lewat scan",
                    description = "Scan batikmu sekarang",
                    modifier = Modifier.padding(horizontal = 30.dp),
                )
                Spacer(Modifier.height(8.dp))
                BatikScanCard(modifier = Modifier.padding(start = 30.dp, end = 30.dp), navController = navController)
            }

            item {
                SectionTitle(
                    title = "Batik daerah",
                    description = "Temukan batik dari seluruh daerah disini",
                    modifier = Modifier.padding(horizontal = 30.dp),
                )
                Spacer(Modifier.height(8.dp))

                FilterChipGroup(
                    // TODO: masih hardcoded, kalau bisa fetch dari origin
                    items = listOf("Yogyakarta", "Pontianak", "Cirebon", "Jakarta", "Solo"),
                    onSelectedChanged = { selected ->
                        selectedOrigin = selected
                        viewModel.fetchBatikByOrigin(selected)
                    },
                    modifier = Modifier.padding(horizontal = 30.dp),
                )

                Spacer(Modifier.height(8.dp))

                when (originState) {
                    is BatikOriginState.Loading -> {
                        ProductOriginList(
                            productOriginList = productOriginList,
                            onProductClick = onProductClick,
                            isLoading = true,
                            modifier = Modifier.padding(horizontal = 30.dp),
                        )
                    }

                    is BatikOriginState.Success -> {
                        ProductOriginList(
                            productOriginList = productOriginList,
                            onProductClick = onProductClick,
                            isLoading = false,
                            modifier = Modifier.padding(horizontal = 30.dp),
                        )
                    }

                    is BatikOriginState.Error -> {
                        Text(
                            text = (originState as BatikOriginState.Error).message,
                            color = Color.Red,
                            style = TextMdSemiBold
                        )
                    }

                    else -> {
                        Text(
                            text = "Produk segera tersedia 😉",
                            color = Color.Red,
                            style = TextMdSemiBold,
                            modifier = Modifier.padding(horizontal = 30.dp),
                        )
                    }
                }
            }
        }
    }
}

