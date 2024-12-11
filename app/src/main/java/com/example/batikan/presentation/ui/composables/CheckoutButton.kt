package com.example.batikan.presentation.ui.composables

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.data.model.cart.AddItemResponse
import com.example.batikan.presentation.ui.screens.ProductDetail
import com.example.batikan.presentation.ui.theme.Gray100
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSmallSemiBold
import com.example.batikan.presentation.ui.theme.White
import com.example.batikan.presentation.viewmodel.AddCartState
import com.example.batikan.presentation.viewmodel.CartViewModel


@Composable
fun CheckoutButton(
    modifier: Modifier = Modifier,
    productId: String,
    productStock: Int,
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel(),
    ) {
    var isCounting by remember { mutableStateOf(false) }
    var count by remember { mutableStateOf(0) }
    var quantity by remember { mutableStateOf(1) }

    val addCartState by cartViewModel.addCartState.collectAsState()

    if (isCounting) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    drawLine(
                        color = Gray100,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = strokeWidth
                    )
                }

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (count > 0) count -= 1
                }) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = null)
                }

                Text(
                    text = "$count",
                    style = TextMdSemiBold,
                    color = TextPrimary,
                )

                IconButton(onClick = {
                    if (count < productStock) count += 1
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }

            when(addCartState) {
                is AddCartState.Idle -> {}
                is AddCartState.Loading -> {}
                is AddCartState.Success -> {
                    val context = LocalContext.current
                    LaunchedEffect(Unit) {
                        Toast.makeText(context, "Produk berhasil ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
                        // Navigate to Cart
                        navController.navigate("home_screen")
//                        navController.navigate("cart_screen") {
//                            launchSingleTop = true
//                        }
                    }
                }
                is AddCartState.Error -> {
                    Text(text="Gagal menambahkan produk")
                }
            }
            Button(
                onClick = {
                    if(count > 0) {
                        cartViewModel.addItemToCart(productId, count)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(40.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Primary600)
            ) {
                Text("Tambah Keranjang")
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    drawLine(
                        color = Gray100,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = strokeWidth,
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(8.dp),
            ) {
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = { isCounting = true },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Primary600)
                ) {
                    Text(
                        text = "Beli sekarang",
                        style = TextSmallSemiBold,
                        color = White
                    )
                }
            }
        }
    }
}
