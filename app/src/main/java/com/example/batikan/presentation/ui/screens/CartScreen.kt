package com.example.batikan.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.composables.BottomNavBar
import com.example.batikan.presentation.ui.composables.CartItemLoading
import com.example.batikan.presentation.ui.composables.CartItemRow
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.Primary200
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
import com.example.batikan.presentation.viewmodel.UserState


data class CartItem(
    val id: String,
    val name: String,
    val imageResources: String,
    val price: Int,
    val count: Int,

    /**
     * val id: String,
     *     val imageResource: String,
     *     val name: String,
     *     val price: String,
     *     val motifDescription: String,
     *     val stockCount: Int,
     *     val soldCount: Int,
     *     val origin: String
     */
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent (
    modifier: Modifier = Modifier,
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val isChecked: Boolean = true
    val cartItems by cartViewModel.cartItemList.collectAsState()
    val getCartState by cartViewModel.getCartState.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()
    val itemUpdateStates = cartViewModel.itemUpdateStates



    LaunchedEffect(Unit) {
        cartViewModel.fetchCartData()
    }

    when (getCartState) {
        is GetCartState.Loading -> {
            CartItemLoading()
            Spacer(modifier = Modifier.padding(20.dp))
            CartItemLoading()
            Spacer(modifier = Modifier.padding(20.dp))
            CartItemLoading()


        }

        is GetCartState.Success -> {
            val product = (getCartState as GetCartState.Success).data.data
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
                    Column {
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
                                    text = "Rp.${totalPrice}",
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
                        BottomNavBar(navController = navController)

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
                            onCountChange = {  id, count -> cartViewModel.updateItemCart(id, count) } ,
                            isLoading = itemUpdateStates[cartItem.id] == true,

                        )
                    }
                }
            }
        }

        is GetCartState.Error -> {
            Text(
                text = (getCartState as GetCartState.Error).message,
                modifier = Modifier.padding(16.dp),
                color = TextSecondary
            )
        }
    }
}
