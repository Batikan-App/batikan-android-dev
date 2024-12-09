        package com.example.batikan.presentation.viewmodel

        import android.util.Log
        import androidx.compose.runtime.State
        import androidx.compose.runtime.mutableStateOf
        import androidx.lifecycle.ViewModel
        import androidx.lifecycle.viewModelScope
        import com.example.batikan.data.model.batik_details.BatikDetailsResponse
        import com.example.batikan.data.model.batik_origin.BatikOriginDetails
        import com.example.batikan.data.model.batik_product.BatikDetails
        import com.example.batikan.data.model.batik_product.BatikList
        import com.example.batikan.data.model.batik_scan.BatikScanResponse
        import com.example.batikan.data.model.batik_search.BatikSearchDetails
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
            
            private val _batikOriginState = MutableStateFlow<BatikOriginState>(BatikOriginState.Idle)
            val batikOriginState: StateFlow<BatikOriginState> get() = _batikOriginState

            // Khusus untuk yang show origin di toko, observenya dari sini
            private val _productOriginList = MutableStateFlow<List<ProductDetail>>(emptyList())
            val productOriginList: StateFlow<List<ProductDetail>> get() = _productOriginList

            private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
            val searchHistory: StateFlow<List<String>> get() = _searchHistory

            private val _searchResults = MutableStateFlow<List<ProductDetail>>(emptyList())
            val searchResults: StateFlow<List<ProductDetail>> get() = _searchResults

            private val _batikSearchState = MutableStateFlow<BatikSearchState>(BatikSearchState.Idle)
            val batikSearchState: StateFlow<BatikSearchState> get() = _batikSearchState

            private fun addSearchHistory(query: String) {
                _searchHistory.value = listOf(query) + _searchHistory.value.filterNot { it == query }
                if (_searchHistory.value.size > 5) {
                    _searchHistory.value = _searchHistory.value.take(5)
                }
            }

            fun clearSearchResults() {
                _searchResults.value = emptyList()
                _batikSearchState.value = BatikSearchState.Idle
            }

            fun searchBatik(query: String) {
                viewModelScope.launch {
                    try {
                        val response = batikRepository.searchBatik(query)
                        val mappedProducts = mapBatikSearch(response)
                        Log.d("BatikSearch", "Mapped products: $mappedProducts")


                        _searchResults.value = mappedProducts

                        Log.d("BatikViewModel", "API Response: ${response.size} items fetched")


                        _productOriginList.value = mappedProducts
                        _batikSearchState.value = BatikSearchState.Success(response)

                    } catch (e: Exception) {
                        Log.d("BatikViewModel", "Error searching batik: ${e.message}")
                        _batikSearchState.value = BatikSearchState.Error("Error: ${e.message}")
                    }
                }
            }

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

            // Fungsi untuk mengambil origin batik
            fun fetchBatikByOrigin(origin: String) {
                viewModelScope.launch {
                    _batikOriginState.value = BatikOriginState.Loading
                    try {
                        val response = batikRepository.getBatikOrigin(origin)
                        val mappedProducts = mapBatikOrigin(response)
                        Log.d("BatikOrigin", "Mapped products: $mappedProducts")

                        _productOriginList.value = mappedProducts
                        _batikOriginState.value = BatikOriginState.Success(response)
                    } catch (e: Exception) {
                        Log.d("BatikOrigin", "Exception: ${e.message}")
                        _batikOriginState.value = BatikOriginState.Error("Error : ${e.message}")
                    }
                }
            }

            fun fetchBatikDetail(batikId: String) {
                viewModelScope.launch {
                    Log.d("BatikDetail", "Fetching batik detail data ...")
                    _batikDetailState.value = BatikDetailState.Loading

                    try {
                        val result = batikRepository.getBatikDetail(batikId)

                        result.fold(
                            onSuccess = { response ->
                                val imageFile = if (response.data.batik.img.isNotEmpty()) response.data.batik.img[0] else ""

                                // Pastikan memetakan objek detail batik dari respons
                                val detail = response.data.batik
                                val mappedBatikDetail = ProductDetail(
                                    id = batikId,
                                    imageResource = imageFile,
                                    name = detail.name,
                                    origin = detail.origin,
                                    soldCount = detail.sold,
                                    stockCount = detail.stock,
                                    price = detail.price,
                                    motifDescription = detail.desc
                                )

                                Log.d("BatikViewModel", "Mapped Batik Detail: $mappedBatikDetail")

                                _productDetailList.value = listOf(mappedBatikDetail)
                                _batikDetailState.value = BatikDetailState.Success(response)
                            },
                            onFailure = { exception ->
                                Log.d("BatikViewModel", "Exception: ${exception.message}")
                                _batikDetailState.value = BatikDetailState.Error("Error: ${exception.message}")
                            }
                        )
                    } catch (e: Exception) {
                        Log.d("BatikViewModel", "Unhandled Exception: ${e.message}")
                        _batikDetailState.value = BatikDetailState.Error("Error: ${e.message}")
                    }
                }
            }

            // TODO: di sini img dari data class Product masih integer, jadi agak rancu typing datanya
            // tipe data img dari model data di data layer juga harus diubah ke List<String> biar bisa muncul,
            // Mungkin biar sesuai sama mapping datanya, kalau String gamau muncul dia.
            private fun mapBatikToProduct(batikItems: List<BatikList>): List<Product> {
                return batikItems.map { item ->
                    val imageFile = if (item.data.img.isNotEmpty()) item.data.img[0] else ""
                    Product(
                        id = item.id,
                        imageResource = imageFile,
                        title = item.data.name,
                        price = "Rp ${item.data.price}" // Format harga
                    )
                }
            }

            private fun mapBatikSearch(batikitems: List<BatikSearchDetails>): List<ProductDetail> {
                return batikitems.map { item ->
                    val imageFile = if(item.img.isNotEmpty()) item.img[0] else ""
                    ProductDetail(
                        id = item.id,
                        imageResource = imageFile,
                        name = item.name,
                        origin = item.origin,
                        soldCount = item.sold,
                        stockCount = item.stock,
                        price = item.price,
                        motifDescription = item.desc
                    )
                }
            }

            private fun mapBatikOrigin(batikitems: List<BatikOriginDetails>): List<ProductDetail> {
                return batikitems.map { item ->
                    val imageFile = if(item.img.isNotEmpty()) item.img[0] else ""
                    ProductDetail(
                        id = item.id,
                        imageResource = imageFile,
                        name = item.name,
                        origin = item.origin,
                        soldCount = item.sold,
                        stockCount = item.stock,
                        price = item.price,
                        motifDescription = item.desc
                    )
                }
            }

            // Fungsi untuk scan batik
            fun scanBatik(imageFile: File) {
                viewModelScope.launch {
                    Log.d("BatikViewModelScan", "Scanning Batik image ...")
                    _scanResultState.value = ScanResultState.Loading

                    try {
                        val resultScan = batikRepository.scanBatik(imageFile = imageFile)
                        Log.d("BatikViewModelScan", "Scan success: $resultScan")

                        _scanResultState.value = when {
                            resultScan.isSuccess -> {
                                val scanResponse = resultScan.getOrNull()!!

                                // Panggil fungsi fetchSimilarBatik
                                fetchSimilarBatikByName(scanResponse.data.data.name)

                                ScanResultState.Success(scanResponse)
                            }
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


            // Fungsi untuk mengambil similar batik
            private fun fetchSimilarBatikByName(batikName: String) {
                viewModelScope.launch {
                    Log.d("BatikViewModel", "Fetching similar batik with name: $batikName")
                    _batikState.value = BatikState.Loading

                    try {
                        // Ambil semua batik dari repository
                        val batikItems = batikRepository.getBatik()

                        // Filter batik berdasarkan nama yang sama dengan hasil scan
                        val similarBatikItems = batikItems.filter {
                            it.data.name.equals(batikName, ignoreCase = true)
                        }

                        Log.d("BatikViewModel", "Similar Batik Items found: ${similarBatikItems.size}")

                        // Mapping ke Product untuk ditampilkan di UI
                        val mappedSimilarProducts = mapBatikToProduct(similarBatikItems)

                        // Update product list dengan batik yang serupa
                        _productList.value = mappedSimilarProducts
                        _batikState.value = BatikState.Success(similarBatikItems)

                    } catch (e: Exception) {
                        Log.d("BatikViewModel", "Exception: ${e.message}")
                        _batikState.value = BatikState.Error("Error: ${e.message}")
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

        sealed class BatikOriginState {
            object Idle: BatikOriginState()
            object Loading: BatikOriginState()
            data class Success(val data: List<BatikOriginDetails>): BatikOriginState()
            data class Error(val message: String): BatikOriginState()
        }

        sealed class BatikSearchState {
            object Idle: BatikSearchState()
            object Loading: BatikSearchState()
            data class Success(val data: List<BatikSearchDetails>): BatikSearchState()
            data class Error(val message: String): BatikSearchState()
        }

        sealed class BatikDetailState {
            object Idle: BatikDetailState()
            object Loading: BatikDetailState()
            data class Success(val batikDetailData: BatikDetailsResponse): BatikDetailState()
            data class Error(val message: String): BatikDetailState()
        }

