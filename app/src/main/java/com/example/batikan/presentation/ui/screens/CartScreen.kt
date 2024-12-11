package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.presentation.ui.composables.BottomNavBar
import com.example.batikan.presentation.ui.composables.CartItemLoading
import com.example.batikan.presentation.ui.composables.CartItemRow
import com.example.batikan.presentation.ui.theme.Primary50
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextQuatenary
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.ui.theme.TextSmallSemiBold
import com.example.batikan.presentation.ui.theme.White
import com.example.batikan.presentation.viewmodel.CartViewModel
import com.example.batikan.presentation.viewmodel.GetCartState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    onPaymentProceed: () -> Unit,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val cartItems by cartViewModel.cartItemList.collectAsState()
    val getCartState by cartViewModel.getCartState.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()
    val itemUpdateStates = cartViewModel.itemUpdateStates

    LaunchedEffect(Unit) {
        cartViewModel.fetchCartData()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Keranjang",
                        style = TextMdSemiBold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate("home_screen") {
                                popUpTo("cart_screen") {
                                    inclusive = true
                                }
                            }
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
                    containerColor = androidx.compose.ui.graphics.Color.White,
                    navigationIconContentColor = TextPrimary,
                    titleContentColor = TextPrimary
                )
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Primary50)
                            .padding(horizontal = 30.dp)
                            .padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Total",
                                style = TextSmallRegular,
                                color = TextQuatenary
                            )
                            Text(
                                text = "Rp.${totalPrice}",
                                style = TextMdSemiBold,
                                color = TextPrimary
                            )
                        }

                        Button(
                            onClick = onPaymentProceed,
                            modifier = modifier
                                .width(110.dp)
                                .height(40.dp),
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = Primary600)
                        ) {
                            Text(
                                text = "Bayar",
                                style = TextSmallSemiBold,
                                color = White
                            )
                        }
                    }
//                    BottomNavBar(navController = navController)
                }
            } else {
                // Tetap tampilkan BottomNavBar meskipun keranjang kosong
//                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        when (getCartState) {
            is GetCartState.Loading -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    items(3) {
                        CartItemLoading()
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

            is GetCartState.Success -> {
                if (cartItems.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.RemoveShoppingCart,
                                contentDescription = "Empty Orders",
                                tint = TextSecondary,
                                modifier = Modifier.size(80.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Belum ada item di keranjang.",
                                style = TextSmallRegular,
                                color = TextSecondary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(horizontal = 30.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(
                            items = cartItems,
                            key = { it.id }
                        ) { cartItem ->
                            CartItemRow(
                                cartItem = cartItem,
                                onCountChange = { id, count ->
                                    cartViewModel.updateItemCart(
                                        id,
                                        count
                                    )
                                },
                                isLoading = itemUpdateStates[cartItem.id] == true,
                            )
                        }
                    }
                }
            }

            is GetCartState.Error -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Text(
                        text = (getCartState as GetCartState.Error).message,
                        style = TextSmallRegular,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

