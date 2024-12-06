package com.example.batikan.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.batikan.R
import com.example.batikan.presentation.ui.composables.CheckoutButton
import com.example.batikan.presentation.ui.composables.ProductDetail
import com.example.batikan.presentation.ui.composables.ProductNamePrice
//import com.example.batikan.presentation.ui.composables.ProductPreview
import com.example.batikan.presentation.ui.composables.ProductStatistic
import com.example.batikan.presentation.ui.theme.BatikanTheme

class DetailProductScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BatikanTheme {
                ProductDetailContent(
                    product = ProductDetail(
                        imageResource = "String",
                        name = "Batik Papua",
                        price = "$20",
                        motifDescription = "Batik Papua adalah salah satu jenis batik khas Indonesia yang berasal dari daerah Papua. Berbeda dengan batik dari daerah lain, batik Papua memiliki ciri khas pada motif dan warna yang menggambarkan budaya, alam, serta kehidupan masyarakat Papua. Motif-motif pada batik Papua seringkali terinspirasi dari bentuk-bentuk alami seperti tumbuhan, hewan khas Papua, dan simbol adat yang memiliki makna mendalam.",
                        stockCount = 10,
                        soldCount = 5,
                        origin = "Bandung"
                    ),
                    onAddToCart = {}
                )

            }
        }
    }
}


data class ProductDetail(
    val imageResource: String,
    val name: String,
    val price: String,
    val motifDescription: String,
    val stockCount: Int,
    val soldCount: Int,
    val origin: String
)


@Composable
fun ProductDetailContent(
    product: ProductDetail,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            CheckoutButton(
                product = product,
                onAddToCart = onAddToCart
            )
        }
    ){ innerPadding ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
//                ProductPreview(imageResource = R.)
                ProductNamePrice(
                    name = product.name,
                    price = product.price
                )
            }

            item {
                ProductStatistic(
                    stock = product.stockCount,
                    sold = product.soldCount,
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }

            item {
                ProductDetail(
                    motifDescription = product.motifDescription,
                    batikOrigin = product.origin,
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }
        }


    }

}

@Preview(showBackground = true)
@Composable
fun DetailProductScreenPreview(){
    BatikanTheme {
        ProductDetailContent(
            product = ProductDetail(
                imageResource = "String",
                name = "Batik Papua",
                price = "$20",
                motifDescription = "Batik Papua adalah salah satu jenis batik khas Indonesia yang berasal dari daerah Papua. Berbeda dengan batik dari daerah lain, batik Papua memiliki ciri khas pada motif dan warna yang menggambarkan budaya, alam, serta kehidupan masyarakat Papua. Motif-motif pada batik Papua seringkali terinspirasi dari bentuk-bentuk alami seperti tumbuhan, hewan khas Papua, dan simbol adat yang memiliki makna mendalam.",
                stockCount = 10,
                soldCount = 5,
                origin = "Bandung"
            ),
            onAddToCart = {}
        )
    }

}
