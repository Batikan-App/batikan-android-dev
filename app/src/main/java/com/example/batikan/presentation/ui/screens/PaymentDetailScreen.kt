package com.example.batikan.presentation.ui.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.batikan.data.Constants
import com.example.batikan.presentation.ui.composables.TextFieldWithTitle
import com.example.batikan.presentation.ui.theme.*
import com.example.batikan.presentation.viewmodel.CartViewModel
import com.example.batikan.presentation.viewmodel.UserViewModel
import com.example.batikan.presentation.viewmodel.UserState
// Midtrans
import com.midtrans.Midtrans
import com.midtrans.httpclient.SnapApi
import com.midtrans.httpclient.error.MidtransError

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentDetailContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    navigateToPayment: (String) -> Unit,
    userViewModel: UserViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    // Midtrans Properties
    Midtrans.serverKey = Constants.MIDTRANS_SERVER_KEY
    Midtrans.clientKey = Constants.MIDTRANS_CLIENT_KEY
    Midtrans.isProduction = false

    // Coroutine Properties
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedShippingOption by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }

    val shippingOptions = listOf("JNE", "J&T", "SiCepat", "POS Indonesia")

    val profileState by userViewModel.userState.collectAsState()
    val cartItems by cartViewModel.cartItemList.collectAsState(initial = emptyList())

    val idOrder: String by cartViewModel.idOrder.collectAsState()

    val totalPrice by cartViewModel.totalPrice.collectAsState()
    Log.d("PaymentDetailTotalPrice", "$totalPrice")

    LaunchedEffect(Unit) {
        userViewModel.fetchUserProfile()
        cartViewModel.fetchCartData()
        cartViewModel.getIdOrder()
    }

    LaunchedEffect(profileState) {
        if (profileState is UserState.Success) {
            val user = (profileState as UserState.Success).data
            name = user.name
            phoneNumber = user.phone
            email = user.email
        }
    }

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
                    onClick = {
                        coroutineScope.launch {
                            // TODO: ngambil quantity terus dipassing ke requestBody
                            val itemDetails = cartItems.map { cartItem ->
                                mapOf(
                                    "id" to cartItem.id,
                                    "price" to cartItem.price,
                                    "quantity" to cartItem.stockCount,
                                    "name" to cartItem.name
                                )
                            }
                            val grossAmount = itemDetails.sumOf { it["price"] as Int * it["quantity"] as Int }

                            val requestBody = mapOf(
                                "transaction_details" to mapOf(
                                    "order_id" to idOrder,
                                    "gross_amount" to grossAmount
                                ),
                                "item_details" to itemDetails,
                                "customer_details" to mapOf(
                                    "first_name" to name,
                                    "email" to email,
                                    "phone" to phoneNumber
                                )
                            )

                            val urlSnap = withContext(Dispatchers.IO) {
                                SnapApi.createTransactionRedirectUrl(requestBody)
                            }
                            val encodedUrlSnap = Uri.encode(urlSnap)
                            navigateToPayment(encodedUrlSnap)
                            Log.d("TestToken", urlSnap)
                        }
                    },
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
                Text(
                    text = "Total Harga: $totalPrice",
                    style = TextMdSemiBold,
                    color = TextSecondary,
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 10.dp)
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
                        readOnly = true,
                        placeholder = { Text("Pilih Pengiriman") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
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
fun CartItemPaymentRow (
    modifier: Modifier = Modifier,
    cartItem: ProductDetail,
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
            AsyncImage(
                model = cartItem.imageResource,
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
                    text = "${cartItem.stockCount} item",
                    style = TextSmallRegular,
                    color = TextSecondary
                )
            }
        }
        Text(
            text = "Rp. ${cartItem.price}",
            style = TextSmallRegular,
            color = TextSecondary
        )
    }
}
