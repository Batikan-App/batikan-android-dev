package com.example.batikan.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.batikan.R
import com.example.batikan.presentation.ui.composables.BatikScanCard
import com.example.batikan.presentation.ui.composables.BottomNavBar
import com.example.batikan.presentation.ui.composables.FilterChipGroup
import com.example.batikan.presentation.ui.composables.PageTitle
import com.example.batikan.presentation.ui.composables.Product
import com.example.batikan.presentation.ui.composables.ProductCardList
import com.example.batikan.presentation.ui.composables.ProductSection
import com.example.batikan.presentation.ui.composables.SearchBar
import com.example.batikan.presentation.ui.composables.SectionTitle
import com.example.batikan.presentation.ui.theme.BatikanTheme

@Composable
fun TokoContent(
    navController: NavController,
    featuredProducts: List<Product>,
    originProduct: List<Product>
){
    var query by remember { mutableStateOf("") }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {BottomNavBar(navController = navController)}
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {
            item {
                PageTitle(
                    title = "Koleksi Batik Kami",
                    description = "Temukan batik kualitas terbaik dari kami",
                    modifier = Modifier.padding(start = 30.dp)
                )
            }

            item {
                SearchBar(
                    query = query,
                    onQueryChanged = { query = it },
                    onSearch = {},
                )
            }

            item {
                ProductSection(
                    title = "Produk keren hari ini",
                    description = "Tentukan pilihan batikmu!",
                    productList = featuredProducts,
                    modifier = Modifier.padding(start = 30.dp)
                )
            }

            item {
                SectionTitle(
                    title = "Cari batik cepat lewat scan",
                    description = "Scan batikmu sekarang",
                    modifier = Modifier.padding(start = 30.dp)
                )
                Spacer(Modifier.height(8.dp))
//                BatikScanCard(modifier = Modifier.padding(start = 30.dp), navController = )
            }

            item () {
                SectionTitle(
                    title = "Batik daerah",
                    description = "Temukan batik dari seluruh daerah disini",
                    modifier = Modifier.padding(start = 30.dp)
                )
                Spacer(Modifier.height(8.dp))
                FilterChipGroup(
                    items = listOf("Semua", "Yogyakarta", "Pekalongan", "Surakarta"),
                    onSelectedChanged = {},
                    modifier = Modifier.padding(start = 30.dp)

                )
                Spacer(Modifier.height(8.dp))
                ProductCardList(
                    productList = originProduct,
                    modifier = Modifier.padding(start = 30.dp)
                )
            }
        }


    }
}

//@Preview(showBackground = true)
//@Composable
//fun TokoScreenPreview() {
//    BatikanTheme {
//        TokoContent(
//            featuredProducts = listOf(
//                Product(R.drawable.batik_new, "Batik A", "$20"),
//                Product(R.drawable.batik_new, "Batik B", "$25"),
//                Product(R.drawable.batik_new, "Batik C", "$30"),
//                Product(R.drawable.batik_new, "Batik D", "$35")
//            ),
//            originProduct = listOf(
//                Product(R.drawable.batik_new, "Batik A", "$20"),
//                Product(R.drawable.batik_new, "Batik B", "$25"),
//                Product(R.drawable.batik_new, "Batik C", "$30"),
//                Product(R.drawable.batik_new, "Batik D", "$35")
//            )
//        )
//    }
//}
