package com.example.batikan.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.batikan.presentation.ui.theme.Secondary500
import com.example.batikan.presentation.ui.theme.TextMdMedium
import com.example.batikan.presentation.ui.theme.TextXsRegular
import com.example.batikan.presentation.ui.theme.White

data class ScanProduct(
    val imageResource: Int,
    val title: String,
    val price: String
)

@Composable
fun ScanProductCard(
    modifier: Modifier = Modifier,
    imageResource: Int,
    title: String,
    price: String
){
    Card (
        modifier = modifier
            .width(140.dp),
        shape = RectangleShape,
    ){
        Column (
            modifier = modifier
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Product Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RectangleShape),
                contentScale = ContentScale.Crop
            )
            Column  (
                modifier = modifier
                    .background(color = Secondary500)
                    .fillMaxWidth()
                    .padding(10.dp)

            ){
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

@Composable
fun ScanProductCardList(
    productList: List<ScanProduct>,
    modifier: Modifier = Modifier
){
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
    ){
        items(productList) { product ->
            ScanProductCard(
                imageResource = product.imageResource,
                title = product.title,
                price = product.price,
            )
            Spacer(Modifier.width(20.dp))
        }
    }
}

@Composable
fun ScanProductSection(
    title: String,
    description: String,
    productList: List<ScanProduct>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SectionTitle(title = title, description = description)
        Spacer(Modifier.height(8.dp))
        ScanProductCardList(
            productList = productList)
    }
}