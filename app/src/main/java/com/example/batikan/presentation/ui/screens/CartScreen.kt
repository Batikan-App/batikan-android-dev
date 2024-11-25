package com.example.batikan.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.batikan.R
import com.example.batikan.presentation.ui.composables.CartItemRow
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.Primary200
import com.example.batikan.presentation.ui.theme.Primary50
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextQuatenary
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.ui.theme.TextSmallSemiBold
import com.example.batikan.presentation.ui.theme.White

class CartScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BatikanTheme {
                CartContent(
                    cartItems = listOf(
                        CartItem(
                            id = "1",
                            name = "Batik Pekalongan",
                            price = 200000,
                            count = 1,
                            isChecked = false,
                            imageResources = R.drawable.batik_new
                        ),
                        CartItem(
                            id = "2",
                            name = "Batik Papua",
                            price = 250000,
                            count = 2,
                            isChecked = true,
                            imageResources = R.drawable.batik_new
                        )
                    ),
                    onItemCheckedChange = { _, _ -> },
                    onItemCountChange = { _, _ -> },
                    onBackClicked = {}

                )
            }
        }
    }
}

data class CartItem(
    val id: String,
    val name: String,
    val imageResources: Int,
    val price: Int,
    val count: Int,
    val isChecked: Boolean = true
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent (
    modifier: Modifier = Modifier,
    cartItems: List<CartItem>,
    onItemCheckedChange: (String, Boolean) -> Unit,
    onItemCountChange: (String, Int) -> Unit,
    onBackClicked: () -> Unit
) {
    Scaffold (
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
                    IconButton(onClick = { onBackClicked() }) {
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
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Primary50)
                    .padding(horizontal = 30.dp)
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,

            ) {
                Column ( ) {
                    Text(
                        text = "Total",
                        style = TextSmallRegular,
                        color = TextQuatenary
                    )
                    Text(
                        text = "Rp. ${cartItems.sumOf { it.price * it.count }}",
                        style = TextMdSemiBold,
                        color = TextPrimary
                    )

                }

                Button(
                    onClick = {  },
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

        }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(cartItems) { cartItem ->
                CartItemRow(
                    cartItem = cartItem,
                    onCheckedChange = { isChecked ->
                        onItemCheckedChange(cartItem.id, isChecked)
                    },
                    onCountChange = { newCount ->
                        onItemCountChange(cartItem.id, newCount)
                    }
                )

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    BatikanTheme {
        CartContent(
            cartItems = listOf(
                CartItem(
                    id = "1",
                    name = "Batik Pekalongan",
                    price = 200000,
                    count = 1,
                    isChecked = false,
                    imageResources = R.drawable.batik_new
                ),
                CartItem(
                    id = "2",
                    name = "Batik Papua",
                    price = 250000,
                    count = 2,
                    isChecked = true,
                    imageResources = R.drawable.batik_new
                )
            ),
            onItemCheckedChange = { _, _ -> },
            onItemCountChange = { _, _ -> },
            onBackClicked = {}

        )
    }
}