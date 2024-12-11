package com.example.batikan.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.presentation.ui.composables.BatikScanCard
import com.example.batikan.presentation.ui.composables.BottomNavBar
import com.example.batikan.presentation.ui.composables.GreetingSection
import com.example.batikan.presentation.ui.composables.NewProductCard
import com.example.batikan.presentation.ui.composables.ProductCardList
import com.example.batikan.presentation.ui.composables.SectionTitle
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.viewmodel.BatikState
import com.example.batikan.presentation.viewmodel.BatikViewModel
import com.example.batikan.presentation.viewmodel.UserState
import com.example.batikan.presentation.viewmodel.UserViewModel

@Composable
fun HomeScreenContent(
    navController: NavController,
    viewModel: BatikViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    onProductClick: (String) -> Unit
){
    val productList by viewModel.productList.collectAsState()
    val profileState by userViewModel.userState.collectAsState()
    val batikState by viewModel.batikState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchBatik()
        userViewModel.fetchUserProfile()
    }

    // Debug log
    Log.d("HomeScreen", "Product List Size: ${productList.size}")
    productList.forEach { product ->
        Log.d("HomeScreen", "Product: $product")
    }

    Scaffold(
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
                when (profileState) {
                is UserState.Loading -> {
//                    CircularProgressIndicator()
                    GreetingSection(
                        userName = "",
                        modifier = Modifier.padding(horizontal = 30.dp),
                        isLoading = true
                    )
                }
                is UserState.Success -> {
                    val user = (profileState as UserState.Success).data
                    GreetingSection(
                        userName = user.name,
                        modifier = Modifier.padding(horizontal = 30.dp),
                        isLoading = false
                    )
                }
                is UserState.Error -> {
                    Text(
                        text = (profileState as UserState.Error).message,
                        color = Color.Red,
                        style = TextMdSemiBold
                    )
                }
                else -> {
                    GreetingSection(
                        userName = "username",
                        modifier = Modifier.padding(horizontal = 30.dp),
                        isLoading = false
                    )
                }
                }
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
                BatikScanCard(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    navController = navController
                )
            }

            item {
                SectionTitle(
                    title = "Produk keren hari ini",
                    description = "Tentukan pilihan batikmu!",
                    actionText = "Lihat semua",
                    onActionClick = { navController.navigate("toko_screen") {
                        popUpTo("home_screen") {
                            inclusive = true
                        }
                    } },
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
                Spacer(Modifier.height(8.dp))
                when (batikState) {
                    is BatikState.Loading -> {
                        ProductCardList(
                            productList = productList,
                            onProductClick = onProductClick,
                            isLoading = true,
                            modifier = Modifier.padding(horizontal = 30.dp)
                        )
                    }

                    is BatikState.Success -> {
                        ProductCardList(
                            productList = productList,
                            onProductClick = onProductClick,
                            isLoading = false,
                            modifier = Modifier.padding(horizontal = 30.dp)
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
                            text = "Produk unggulan segera tersedia 😉",
                            color = Color.Red,
                            style = TextMdSemiBold,
                            modifier = Modifier.padding(horizontal = 30.dp)
                        )
                    }
                }
            }
        }
    }
}