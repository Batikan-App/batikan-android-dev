package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.batikan.data.model.batik_product.BatikList
import com.example.batikan.presentation.ui.composables.CheckoutButton
import com.example.batikan.presentation.ui.composables.Product
//import com.example.batikan.presentation.ui.composables.CheckoutButton
import com.example.batikan.presentation.ui.composables.ProductDetail
import com.example.batikan.presentation.ui.composables.ProductNamePrice
import com.example.batikan.presentation.ui.composables.ProductPreview
//import com.example.batikan.presentation.ui.composables.ProductPreview
import com.example.batikan.presentation.ui.composables.ProductStatistic
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.viewmodel.BatikDetailState
import com.example.batikan.presentation.viewmodel.BatikViewModel
import com.example.batikan.presentation.viewmodel.CartViewModel

/**
 * TODO: Nambahin icon cart biar user bisa buka cart di pojok kanan atas
 */

data class ProductDetail(
    val id: String,
    val imageResource: String,
    val name: String,
    val price: Int,
    val motifDescription: String,
    val stockCount: Int,
    val soldCount: Int,
    val origin: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productDetailList: List<ProductDetail>,
    viewModel: BatikViewModel = hiltViewModel(),
    productId: String,
    navController: NavController
){
    LaunchedEffect(productId) {
        viewModel.fetchBatikDetail(productId)
    }


    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detail Product",
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
//        bottomBar = {
//            val productCheckoutId = productDetailList[0].id
//            val productStock = productDetailList[0].stockCount
//
//        }
    ){ innerPadding ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(productDetailList) { product ->

                ProductPreview(
                    imageResource = product.imageResource
                )

                ProductNamePrice(
                    name = product.name,
                    price = product.price,
                )

                ProductStatistic(
                    stock = product.stockCount,
                    sold = product.soldCount,
                    type = "Batik",
                    modifier = Modifier.padding(horizontal = 30.dp)
                )

                ProductDetail(
                    motifDescription = product.motifDescription,
                    batikOrigin = product.origin,
                    modifier = Modifier.padding(horizontal = 30.dp)
                )

                CheckoutButton(
                    productId = product.id,
                    productStock = product.stockCount,
                    navController = navController
                )
            }
        }
    }
}
