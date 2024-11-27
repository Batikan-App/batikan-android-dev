package com.example.batikan.presentation.ui.screens

import android.net.Uri
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.batikan.R
import com.example.batikan.presentation.ui.composables.AboutBatik
import com.example.batikan.presentation.ui.composables.CardScanResult
import com.example.batikan.presentation.ui.composables.PageTitle
import com.example.batikan.presentation.ui.composables.Product
import com.example.batikan.presentation.ui.composables.ProductSection
import com.example.batikan.presentation.ui.composables.SectionTitle
import com.example.batikan.presentation.ui.composables.VisualTryOnCard
import com.example.batikan.presentation.ui.theme.BatikanTheme

data class ScanResult(
    val name: String,
    val aboutMotif: String,
    val origin: String
)

@Composable
fun ScanResultContent(
    similiarProduct: List<Product>,
    result: ScanResult,
    modifier: Modifier = Modifier,
    photoUri: String?
){
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {}
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                PageTitle(
                    title = "Hasil Scan Batik",
                    description = "Hasil scan batikmu",
                    modifier = Modifier.padding(start = 30.dp)
                )
            }

            item {
                CardScanResult(
                    name = result.name,
                    origin = result.origin,
                    imageResource = R.drawable.batik_new,
                    onActionClick = {},
                    modifier = Modifier.padding(start = 30.dp),
                    photoUri = photoUri
                )
            }

            item {
                AboutBatik(
                    description = result.aboutMotif,
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }

            item {
                SectionTitle(
                    title = "Visual Try-On",
                    description = "Coba batik yang kamu suka secara virtual",
                    modifier = Modifier.padding(start = 30.dp)
                )
                Spacer(Modifier.height(8.dp))
                VisualTryOnCard(
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }

            item {
                ProductSection(
                    title = "Produk terkait",
                    description = "Beli batik",
                    productList = similiarProduct,
                    modifier = modifier.padding(start = 30.dp)

                )
            }
        }
    }

}
//
//@Preview(showBackground = true)
//@Composable
//fun ScanResultScreenPreview(){
//    BatikanTheme {
//        ScanResultContent(
//            result = ScanResult(
//                name = "Batik Papua",
//                aboutMotif = "Batik Papua adalah salah satu jenis batik khas Indonesia yang berasal dari daerah Papua. Berbeda dengan batik dari daerah lain, batik Papua memiliki ciri khas pada motif dan warna yang menggambarkan budaya, alam, serta kehidupan masyarakat Papua. Motif-motif pada batik Papua seringkali terinspirasi dari bentuk-bentuk alami seperti tumbuhan, hewan khas Papua, dan simbol adat yang memiliki makna mendalam.",
//                origin = "Papua Barat"
//            ),
//            similiarProduct = listOf(
//                Product(R.drawable.batik_new, "Batik A", "$20"),
//                Product(R.drawable.batik_new, "Batik B", "$25"),
//                Product(R.drawable.batik_new, "Batik C", "$30"),
//                Product(R.drawable.batik_new, "Batik D", "$35")
//            )
//
//        )
//    }
//}