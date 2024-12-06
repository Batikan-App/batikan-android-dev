    package com.example.batikan.presentation.viewmodel

    import android.util.Log
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.batikan.R
    import com.example.batikan.data.model.batik_product.BatikDetails
    import com.example.batikan.data.model.batik_product.BatikList
    import com.example.batikan.data.model.batik_scan.BatikScanResponse
    import com.example.batikan.domain.repositories.BatikRepository
    import com.example.batikan.presentation.ui.composables.Product
    import com.example.batikan.presentation.ui.screens.ProductDetail
    import dagger.hilt.android.lifecycle.HiltViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.launch
    import java.io.File
    import javax.inject.Inject

    @HiltViewModel
    class BatikViewModel @Inject constructor(
        private val batikRepository: BatikRepository
    ) : ViewModel() {

        private val _batikState = MutableStateFlow<BatikState>(BatikState.Idle)
        val batikState: StateFlow<BatikState> get() = _batikState

        private val _batikDetailState = MutableStateFlow<BatikDetailState>(BatikDetailState.Idle)
        val batikDetailState: StateFlow<BatikDetailState> get() = _batikDetailState

        private val _productList = MutableStateFlow<List<Product>>(emptyList())
        val productList: StateFlow<List<Product>> get() = _productList

        private val _productDetailList = MutableStateFlow<List<ProductDetail>>(emptyList())
        val productDetailList: StateFlow<List<ProductDetail>> get() = _productDetailList

        private val _scanResultState = MutableStateFlow<ScanResultState>(ScanResultState.Idle)
        val scanResultState: StateFlow<ScanResultState> get() = _scanResultState

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

//        fun fetchBatikDetail(batikId: String) {
//            viewModelScope.launch {
//                Log.d("BatikDetail", "Fetching batik detail data ...")
//                _batikDetailState.value = BatikDetailState.Loading
//
//                try {
//                    val batikDetailItem = batikRepository.getBatikDetail(batikId)
//
//                    val mappedBatikDetail =
//                }
//            }
//        }

        // TODO: di sini img dari data class Product masih integer, jadi agak rancu typing datanya
        // tipe data img dari model data di data layer juga harus diubah ke List<String> biar bisa muncul,
        // Mungkin biar sesuai sama mapping datanya, kalau String gamau muncul dia.
        private fun mapBatikToProduct(batikItems: List<BatikList>): List<Product> {
            return batikItems.map { item ->
                val imageFile = if (item.data.img.isNotEmpty()) item.data.img[0] else ""
                Product(
                    imageResource = imageFile,
                    title = item.data.name,
                    price = "Rp ${item.data.price}" // Format harga
                )
            }
        }

//        private fun mapBatikDetail(batikDetailItem: List<BatikList>): List<ProductDetail> {
//            return batikDetailItem.map { item ->
//
//            }
//        }

        // Fungsi untuk scan batik
        fun scanBatik(imageFile: File) {
            viewModelScope.launch {
                Log.d("BatikViewModelScan", "Scanning Batik image ...")
                _scanResultState.value = ScanResultState.Loading

                try {

                    val resultScan = batikRepository(imageFile = imageFile)
                    Log.d("BatikViewModelScan", "Scan success: $resultScan")

                    _scanResultState.value = when {
                        resultScan.isSuccess -> ScanResultState.Success(resultScan.getOrNull()!!)
                        else -> ScanResultState.Error(
                            resultScan.exceptionOrNull()?.message ?: "Unknown Error"
                        )
                    }
                } catch (e: Exception) {
                    Log.d("BatikViewModel", "Exception: ${e.message}")
                    _scanResultState.value = ScanResultState.Error("Error: ${e.message}")
                }
            }
        }
    }

    sealed class ScanResultState {
        object Idle : ScanResultState()
        object Loading : ScanResultState()
        data class Success(val response: BatikScanResponse): ScanResultState()
        data class Error(val message: String) : ScanResultState()
    }

    sealed class BatikState {
        object Idle : BatikState()
        object Loading : BatikState()
        data class Success(val data: List<BatikList>) : BatikState()
        data class Error(val message: String) : BatikState()
    }

    sealed class BatikDetailState {
        object Idle: BatikDetailState()
        object Loading: BatikDetailState()
        data class Success(val batikDetailData: List<BatikList>): BatikDetailState()
        data class Error(val message: String): BatikDetailState()
    }