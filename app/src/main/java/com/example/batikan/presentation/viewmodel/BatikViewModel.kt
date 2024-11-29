package com.example.batikan.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.batikan.R
import com.example.batikan.data.remote.Batik
import com.example.batikan.domain.repositories.BatikRepository
import com.example.batikan.presentation.ui.composables.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BatikViewModel @Inject constructor(
    private val batikRepository: BatikRepository
) : ViewModel() {

    private val _batikState = MutableStateFlow<BatikState>(BatikState.Idle)
    val batikState: StateFlow<BatikState> get() = _batikState

    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> get() = _productList

    fun fetchBatik() {
        viewModelScope.launch {
            Log.d("BatikViewModel", "Fetching Batik data...")
            _batikState.value = BatikState.Loading

            try {
                val batikItems = batikRepository.getBatik() // Repositori sudah otomatis menggunakan AuthInterceptor
                Log.d("BatikViewModel", "API Response: ${batikItems.size} items fetched")

                val mappedProducts = mapBatikToProduct(batikItems)
                Log.d("BatikViewModel", "Mapped Products: $mappedProducts")

                _productList.value = mappedProducts // Update daftar produk
                _batikState.value = BatikState.Success(batikItems)

            } catch (e: Exception) {
                Log.d("BatikViewModel", "Exception: ${e.message}")
                _batikState.value = BatikState.Error("Error: ${e.message}")
            }
        }
    }

    private fun mapBatikToProduct(batikItems: List<Batik>): List<Product> {
        return batikItems.map { item ->
            Product(
                imageResource = R.drawable.batik_new,
                title = item.data.name,
                price = "Rp ${item.data.price}" // Format harga
            )
        }
    }
}

sealed class BatikState {
    object Idle : BatikState()
    object Loading : BatikState()
    data class Success(val data: List<Batik>) : BatikState()
    data class Error(val message: String) : BatikState()
}