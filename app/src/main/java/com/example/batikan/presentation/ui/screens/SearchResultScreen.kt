package com.example.batikan.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.batikan.presentation.ui.composables.ProductCard
import com.example.batikan.presentation.ui.composables.ProductSection
import com.example.batikan.presentation.ui.composables.SearchBar
import com.example.batikan.presentation.ui.theme.TextMdSemiBold
import com.example.batikan.presentation.ui.theme.TextPrimary
import com.example.batikan.presentation.viewmodel.BatikSearchState
import com.example.batikan.presentation.viewmodel.BatikState
import com.example.batikan.presentation.viewmodel.BatikViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter


@OptIn(FlowPreview::class)
@Composable
fun SearchResultScreen(
    navController: NavController,
    initialQuery: String,
    viewModel: BatikViewModel = hiltViewModel()
) {
    // State untuk inget current search query
    var query by remember { mutableStateOf(initialQuery) }

    val searchResults by viewModel.searchResults.collectAsState()
    val searchState by viewModel.batikSearchState.collectAsState()

    LaunchedEffect(query) {
        if (query.isBlank()) {
            viewModel.clearSearchResults()
        } else {
            snapshotFlow { query }
                .filter { it.isNotBlank() }
                .debounce(300)
                .collect {
                    viewModel.searchBatik(it)
                }
        }
    }

    Scaffold(
        topBar = {
            IconButton(onClick = {
                navController.navigateUp()
            },
                modifier = Modifier.padding(top = 30.dp, bottom = 8.dp, start = 8.dp, end = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = TextPrimary
                )
            }
            SearchBar(
                query = query,
                onQueryChanged = {
                    query = it
                },

                onSearch = {
                    if(query.isNotBlank()) {
                        viewModel.searchBatik(query)
                    }
                },

                modifier = Modifier.padding(start = 24.dp, top = 20.dp, bottom = 16.dp, end = 2.dp)
            )
        }
    ) { innerPadding ->
        // Baca state query biar bisa manggil api dinamis
        when {
            // Kalo query kosong, empty state
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

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(innerPadding),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp), // Jarak horizontal antar item
                    verticalArrangement = Arrangement.spacedBy(20.dp)    // Jarak vertikal antar item
                ) {
                    items(searchResults) { product ->
                        Spacer(Modifier.height(8.dp))
                        when (searchState) {
                            is BatikSearchState.Loading -> {
                                ProductCard(
                                    imageResource = product.imageResource,
                                    title = product.name,
                                    price = "Rp${product.price}",
                                    onClick = {
                                        navController.navigate("detail_product_screen/${product.id}") {
                                            popUpTo("search_result_screen") {
                                                inclusive = true
                                            }
                                        }
                                    },
                                    isLoading = true
                                )
                            }

                            is BatikSearchState.Success -> {
                                ProductCard(
                                    imageResource = product.imageResource,
                                    title = product.name,
                                    price = "Rp${product.price}",
                                    onClick = {
                                        navController.navigate("detail_product_screen/${product.id}") {
                                            popUpTo("search_result_screen") {
                                                inclusive = true
                                            }
                                        }
                                    },
                                    isLoading = false
                                )
                            }

                            is BatikSearchState.Error -> {
                                Text(
                                    text = (searchState as BatikSearchState.Error).message,
                                    color = Color.Red,
                                    style = TextMdSemiBold
                                )
                            }

                            else -> {
                                Text(
                                    text = "Produk segera tersedia 😉",
                                    color = Color.Red,
                                    style = TextMdSemiBold,
                                    modifier = Modifier.padding(horizontal = 30.dp)

                                )
                            }
                        }
                    }
                }
            }
        }
    }
}