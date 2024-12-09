package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.presentation.ui.composables.CartItemRow
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSmallSemiBold
import com.example.batikan.presentation.ui.theme.White
import com.example.batikan.presentation.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationScreen(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val cartItems by cartViewModel.cartItemList.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Konfirmasi Pesanan",
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
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Total",
                        style = TextSmallSemiBold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Rp.${totalPrice}",
                        style = TextMdSemiBold,
                        color = TextPrimary
                    )
                }
                Button(
                    onClick = { /* Arahkan ke halaman pembayaran */ },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth(0.4f),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Primary600)
                ) {
                    Text(
                        text = "Lanjut Bayar",
                        style = TextSmallSemiBold,
                        color = White
                    )
                }
            }
        }
    ) { innerPadding ->
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
                    onCountChange = { _, _ -> }, // Tidak memungkinkan update di layar ini
                    isLoading = false
                )
            }
        }
    }
}
