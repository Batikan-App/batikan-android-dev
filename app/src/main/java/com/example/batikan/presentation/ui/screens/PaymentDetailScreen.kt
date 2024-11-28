package com.example.batikan.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.theme.BatikanTheme
import com.example.batikan.presentation.ui.theme.Primary50
import com.example.batikan.presentation.ui.theme.Primary600
import com.example.batikan.presentation.ui.theme.TextMdMedium
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.ui.theme.TextQuatenary
import com.example.batikan.presentation.ui.theme.TextSecondary
import com.example.batikan.presentation.ui.theme.TextSmallMedium
import com.example.batikan.presentation.ui.theme.TextSmallRegular
import com.example.batikan.presentation.ui.theme.TextSmallSemiBold
import com.example.batikan.presentation.ui.theme.White


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentDetailContent(
    modifier: Modifier = Modifier,
    cartItems: List<CartItem>,
    navController: NavController


) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedShippingOption by remember { mutableStateOf("") }
    val shippingOptions = listOf("JNE", "J&T", "SiCepat", "POS Indonesia")
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pembayaran",
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
                    .background(color = Primary50)
                    .padding(horizontal = 30.dp)
                    .padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {

                Button(
                    onClick = { },
                    modifier = modifier
                        .fillMaxWidth()
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
                CartItemPaymentRow(
                    cartItem = cartItem
                )
            }

            item {
                TextFieldWithTitle(
                    title = "Nama",
                    value = name,
                    onValueChange = { name = it }
                )
            }

            item {
                TextFieldWithTitle(
                    title = "Nomor Telepon",
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    keyboardType = KeyboardType.Phone
                )
            }

            item {
                TextFieldWithTitle(
                    title = "Alamat Lengkap",
                    value = address,
                    onValueChange = { address = it },
                    singleLine = false,
                    maxLines = 5
                )
            }

            item {
                Text(
                    text = "Pilihan Pengiriman",
                    style = TextSmallMedium,
                    color = TextSecondary,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .padding(bottom = 4.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedShippingOption,
                        onValueChange = { },
                        label = { Text(text = "Pilih Pengiriman") },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        shippingOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedShippingOption = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun TextFieldWithTitle(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1,
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp)
    ) {
        Text(
            text = title,
            style = TextSmallMedium,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine,
            maxLines = maxLines
        )
    }
}

@Composable
fun CartItemPaymentRow (
    modifier: Modifier = Modifier,
    cartItem: CartItem,
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = cartItem.imageResources),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 90.dp, height = 90.dp)
            )
            Column () {
                Text(
                    text = cartItem.name,
                    style = TextMdMedium,
                    color = TextPrimary
                )
                Text(
                    text = "${cartItem.count} item",
                    style = TextSmallRegular,
                    color = TextSecondary
                )
            }
        }
        Text(
            text = "Rp. ${cartItem.price}",
            style = TextSmallRegular,
            color = TextSecondary,

            )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PaymentDetailScreenPreview() {
//    BatikanTheme {
//        PaymentDetailContent(
//            cartItems = listOf(
//                CartItem(
//                    id = "1",
//                    name = "Batik Pekalongan",
//                    price = 200000,
//                    count = 1,
//                    isChecked = false,
//                    imageResources = R.drawable.batik_new
//                ),
//                CartItem(
//                    id = "2",
//                    name = "Batik Papua",
//                    price = 250000,
//                    count = 2,
//                    isChecked = true,
//                    imageResources = R.drawable.batik_new
//                )
//            )
//        )
//    }
//}