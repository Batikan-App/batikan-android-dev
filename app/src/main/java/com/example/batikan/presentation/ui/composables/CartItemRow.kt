package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.batikan.presentation.ui.screens.CartItem
import com.example.batikan.presentation.ui.theme.TextMdMedium
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallRegular

@Composable
fun CartItemRow (
    modifier: Modifier = Modifier,
    cartItem: CartItem,
    onCheckedChange: (Boolean) -> Unit,
    onCountChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Checkbox(
            checked = cartItem.isChecked,
            onCheckedChange = onCheckedChange
        )
        Image(
            painter = painterResource(id = cartItem.imageResources),
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
                text = "Rp. ${cartItem.price}",
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
                    onClick = { if (cartItem.count > 1) onCountChange(cartItem.count - 1)}
                ) {
                    Icon(imageVector = Icons.Default.Remove, contentDescription = null)
                }
                Text(
                    text = cartItem.count.toString(),
                    style = TextMdSemiBold,
                    color = TextPrimary,
                )
                IconButton(
                    // ToDo : Add checking max item
                    onClick = {onCountChange(cartItem.count + 1)}
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }
    }
}