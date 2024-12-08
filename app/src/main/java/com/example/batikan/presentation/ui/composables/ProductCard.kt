package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.batikan.presentation.ui.screens.ProductDetail
import com.example.batikan.presentation.ui.theme.Gray100
import com.example.batikan.presentation.ui.theme.Gray200
import com.example.batikan.presentation.ui.theme.Secondary500
import com.example.batikan.presentation.ui.theme.TextMdMedium
import com.example.batikan.presentation.ui.theme.TextXsRegular
import com.example.batikan.presentation.ui.theme.White
import com.example.batikan.presentation.ui.util.shimmerEffect


data class Product(
    val id: String,
    val imageResource: String,
    val title: String,
    val price: String
)

@Composable
fun ProductSearchResultList(
    productOriginList: List<ProductDetail>,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier
){

}

@Composable
fun ProductOriginList(
    productOriginList: List<ProductDetail>,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean
){

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
    ){
        items(productOriginList) { product ->
            ProductCard(
                imageResource = product.imageResource,
                title = product.name,
                price = "Rp${product.price}",
                onClick = { onProductClick(product.id) },
                isLoading = isLoading
            )
            Spacer(Modifier.width(20.dp))
        }
    }
}

@Composable
fun ProductCardList(
    productList: List<Product>,
    onProductClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean
){

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
    ){
        items(productList) { product ->
            ProductCard(
                imageResource = product.imageResource,
                title = product.title,
                price = product.price,
                onClick = { onProductClick(product.id) },
                isLoading = isLoading
            )
            Spacer(Modifier.width(20.dp))
        }
    }
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    imageResource: String,
    title: String,
    price: String,
    onClick: () -> Unit,
    isLoading: Boolean
){
    Card (
        modifier = modifier
            .width(140.dp)
            .clickable { onClick() },
        shape = RectangleShape,
    ){
        Column (
            modifier = modifier
        ) {
            if (isLoading) {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .shimmerEffect()
                )
            } else {
                AsyncImage(
                    model = imageResource,
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RectangleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Column  (
                modifier = modifier
                    .background( color = if (isLoading) Gray200 else Secondary500)
                    .fillMaxWidth()
                    .padding(10.dp)

            ){
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(20.dp)
                            .shimmerEffect()
                    )
                } else {
                    Text(
                        text = title,
                        style = TextMdMedium,
                        color = White
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = price,
                        style = TextXsRegular,
                        color = White
                    )
                }
            }
        }
    }
}

@Composable
fun ProductSection(
    navController: NavController,
    title: String,
    description: String,
    productList: List<Product>,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SectionTitle(title = title, description = description, modifier = Modifier.padding(start = 30.dp))
        Spacer(Modifier.height(8.dp))
        ProductCardList(
            productList = productList,
            onProductClick = { batikId ->
                navController.navigate("detail_product_screen/$batikId")
            },
            isLoading = isLoading
        )
    }
}