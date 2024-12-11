package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.batikan.presentation.ui.screens.ProductDetail
import com.example.batikan.presentation.ui.theme.TextMdMedium
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.ui.util.shimmerEffect
import com.example.batikan.presentation.viewmodel.CartViewModel


@Composable
fun CartItemLoading(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 90.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(112.dp)
                .shimmerEffect()
        )
        Column () {
            Box (
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(20.dp)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box (
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(10.dp)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .shimmerEffect()
                )


            }
        }
    }
}

@Composable
fun CartItemRow (
    modifier: Modifier = Modifier,
    cartItem: ProductDetail,
//    onCheckedChange: (Boolean) -> Unit,
    onCountChange: (String, Int) -> Unit,
    isLoading: Boolean,
    cartViewModel: CartViewModel = hiltViewModel()

) {
    var stockCount by remember(cartItem.id) { mutableStateOf(cartItem.stockCount) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//        Checkbox(
//            checked = cartItem.isChecked,
//            onCheckedChange = onCheckedChange
//        )
            AsyncImage(
                model = cartItem.imageResource,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 112.dp, height = 112.dp)
            )
            Column () {
                Text(
                    text = cartItem.name,
                    style = TextMdMedium,
                    color = TextPrimary
                )
                Text(
                    text = "Rp. ${cartItem.price * cartItem.stockCount}",
                    style = TextSmallRegular,
                    color = TextSecondary
                )
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    IconButton(
                        onClick = {
                            if (cartItem.stockCount >= 0) {
//                            onCountChange(cartItem.stockCount - 1)
//                            cartViewModel.updateItemCart(cartItem.id, cartItem.stockCount - 1)
//                            cartViewModel.fetchCartData()
                                stockCount -= 1
                                if(stockCount == 0) {
                                    cartViewModel.fetchCartData()
                                }
                                onCountChange(cartItem.id, stockCount)


//                            } else {
//                                // ToDo : Remove item from cart
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = null)
                    }
                    Text(
                        text = cartItem.stockCount.toString(),
                        style = TextMdSemiBold,
                        color = TextPrimary,
                    )
                    IconButton(
                        onClick = {
//                        onCountChange(cartItem.stockCount + 1)
                            //** ganti dengan stok maksimal yang sesuai
                            if (cartItem.stockCount < 100 ) {
                                stockCount += 1
                                onCountChange(cartItem.id, stockCount)

                            }
                            // ToDo : Add checking max stok
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            }
        }
}