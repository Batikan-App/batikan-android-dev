package com.example.batikan.presentation.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.batikan.data.model.batik_product.BatikList
import com.example.batikan.data.model.batik_search.BatikSearchDetails
import com.example.batikan.data.model.cart.AddItemResponse
import com.example.batikan.data.model.cart.CartItemData
import com.example.batikan.data.model.cart.CartItemList
import com.example.batikan.data.model.cart.CartResponse
import com.example.batikan.data.model.cart.UpdateItemResponse
import com.example.batikan.domain.repositories.BatikRepository
import com.example.batikan.domain.repositories.CartRepository
import com.example.batikan.presentation.ui.composables.Product
import com.example.batikan.presentation.ui.screens.CartItem
import com.example.batikan.presentation.ui.screens.ProductDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _getCartState = MutableStateFlow<GetCartState>(GetCartState.Loading)
    val getCartState: StateFlow<GetCartState> get() = _getCartState

    private val _addCartState = MutableStateFlow<AddCartState>(AddCartState.Loading)
    val addCartState: StateFlow<AddCartState> get() = _addCartState

    private val _cartItemList = MutableStateFlow<List<ProductDetail>>(emptyList())
    val cartItemList: StateFlow<List<ProductDetail>> = _cartItemList

    private val _itemUpdateStates = mutableStateMapOf<String, Boolean>()
    val itemUpdateStates: Map<String, Boolean> get() = _itemUpdateStates

    private val _totalPrice = MutableStateFlow(0L)
    val totalPrice: StateFlow<Long> get() = _totalPrice

    init {
        viewModelScope.launch {
            cartItemList.collect { items ->
                _totalPrice.value = items.sumOf { it.price.toLong() * it.stockCount.toLong() }
            }
        }
    }

    fun fetchCartData() {
        viewModelScope.launch {
            _getCartState.value = GetCartState.Loading

            val result = cartRepository.getCartData()
            result.fold(
                onSuccess = { data ->
                    val cartItems = mapCartToProductDetail(data.data.cartItem)
                    _cartItemList.value = cartItems
                    _getCartState.value = GetCartState.Success(data)

                },
                onFailure = { error ->
                    _getCartState.value = GetCartState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }

    private fun mapCartToProductDetail(cartItem: (List<CartItemData>)): List<ProductDetail> {
        return cartItem.map { item ->
            val imageFile = if (item.img.isNotEmpty()) item.img[0] else ""
            ProductDetail(
                id = item.id,
                name = item.name,
                price = item.price,
                stockCount = item.quantity,
                imageResource = imageFile,
                motifDescription = "",
                origin = "",
                soldCount = 0
            )
        }
    }

    fun addItemToCart(productId: String, quantity: Int) {
        viewModelScope.launch {
            try {
                val response = cartRepository.addItemToCart(productId, quantity)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _addCartState.value = AddCartState.Success(body)
                    } else {
                        _addCartState.value = AddCartState.Error("Error")
                    }
                }
            } catch (e: HttpException) {
                _addCartState.value = AddCartState.Error("Network error: ${e.message}")
            }
        }
    }
    fun updateItemCart(productId: String, quantity: Int) {
        viewModelScope.launch {
            // Optimistically update local state
            _cartItemList.value = _cartItemList.value.map { item ->
                if (item.id == productId) item.copy(stockCount = quantity) else item
            }
            _itemUpdateStates[productId] = true

            // Update server-side data
            val response = cartRepository.updateCartItem(productId, quantity)
            if (!response.isSuccessful) {
                // Revert the state if the update fails
                _cartItemList.value = _cartItemList.value.map { item ->
                    if (item.id == productId) item.copy(stockCount = item.stockCount) else item
                }
            }

            _itemUpdateStates[productId] = false
        }
    }


//    fun addItemToCart(productDetail: ProductDetail, quantity: Int) {
//        val currentList = _cartItemList.value.toMutableList()
//        val newItem = productDetail.toCartItem(quantity)
//
//        // Periksa apakah item sudah ada
//        val existingItem = currentList.find { it.id == newItem.id }
//        if (existingItem != null) {
//            // Perbarui jumlah jika sudah ada
//            val updatedItem = existingItem.copy(count = existingItem.count + quantity)
//            currentList[currentList.indexOf(existingItem)] = updatedItem
//        } else {
//            // Tambahkan item baru
//            currentList.add(newItem)
//        }
//
//        _cartItemList.value = currentList
//    }
//
//    fun ProductDetail.toCartItem(quantity: Int): CartItem {
//        return CartItem(
//            id = this.id,
//            name = this.name,
//            imageResources = this.imageResource,
//            price = this.price.replace(",", "").replace("Rp ", "").toInt(), // Menghapus format string harga
//            count = quantity,
//            isChecked = true
//        )
//    }

}

sealed class GetCartState {
    object Loading: GetCartState()
    data class Success(val data: CartResponse) : GetCartState()
    data class Error(val message: String): GetCartState()
}

sealed class AddCartState {
    object Idle: AddCartState()
    object Loading: AddCartState()
    data class Success(val data: AddItemResponse): AddCartState()
    data class Error(val message: String): AddCartState()
}

sealed class UpdateCartState {
    object Idle: UpdateCartState()
    object Loading: UpdateCartState()
    data class Success(val data: UpdateItemResponse): UpdateCartState()
    data class Error(val message: String): UpdateCartState()

}