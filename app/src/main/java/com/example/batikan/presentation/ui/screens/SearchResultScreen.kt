package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.presentation.ui.composables.ProductCard
import com.example.batikan.presentation.ui.composables.ProductCardList
import com.example.batikan.presentation.ui.composables.ProductOriginList
import com.example.batikan.presentation.ui.composables.SearchBar
import com.example.batikan.presentation.viewmodel.BatikViewModel


@Composable
fun SearchResultScreen(
    navController: NavController,
    initialQuery: String,
    viewModel: BatikViewModel = hiltViewModel()
) {
    // State untuk inget current search query
    var query by remember { mutableStateOf(initialQuery) }

    val searchResults by viewModel.searchResults.collectAsState()

    LaunchedEffect(query) {
        viewModel.searchBatik(query)
    }

    Scaffold(
        topBar = {
            SearchBar(
                query = query,
                onQueryChanged = {
                    query = it
                },

                onSearch = {
                    if(query.isNotBlank()) {
                        viewModel.searchBatik(query)
                        // Navigasi ke screen yang sama setiap query baru
                        navController.navigate("search_result_screen/$query") {
                            // pop up ke destinasi yang ini lagi untuk menghindari stacked screen
                            popUpTo("search_result_screen/$query") {
                                inclusive = true
                            }
                        }
                    }
                },
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { innerPadding ->
        // Content based on query and search results
        when {
            // If query is blank, show empty state
            query.isBlank() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Masukkan kata kunci pencarian",
                        textAlign = TextAlign.Center
                    )
                }
            }

            // If query is not blank but no results found
            searchResults.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada hasil untuk \"$query\"",
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Show results if query is not blank and results exist
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(searchResults) {
                        // TODO: implement grid biar ga numpuk jadi row besok
                        ProductOriginList(
                            productOriginList = searchResults,
                            onProductClick = { batikId ->
                                navController.navigate("detail_product_screen/$batikId")
                            }
                        )
                    }
                }
            }
        }
    }
}