package com.example.batikan.presentation.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.batikan.data.model.batik_product.BatikList
import com.example.batikan.data.model.batik_search.BatikSearchDetails
import com.example.batikan.data.model.cart.AddItemResponse
import com.example.batikan.data.model.cart.CartDeleteResponse
import com.example.batikan.data.model.cart.CartItemData
import com.example.batikan.data.model.cart.CartItemList
import com.example.batikan.data.model.cart.CartResponse
import com.example.batikan.data.model.cart.UpdateItemResponse
import com.example.batikan.domain.repositories.BatikRepository
import com.example.batikan.domain.repositories.CartRepository
import com.example.batikan.presentation.ui.composables.Product
import com.example.batikan.presentation.ui.screens.ProductDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
) : ViewModel() {
    private val _idOrder = MutableStateFlow("")
    val idOrder: StateFlow<String> = _idOrder

    private val _getCartState = MutableStateFlow<GetCartState>(GetCartState.Loading)
    val getCartState: StateFlow<GetCartState> get() = _getCartState

    private val _addCartState = MutableStateFlow<AddCartState>(AddCartState.Loading)
    val addCartState: StateFlow<AddCartState> get() = _addCartState

    private val _deleteCartState = MutableStateFlow<Result<Unit>?>(null)
    val deleteCartState: StateFlow<Result<Unit>?> = _deleteCartState.asStateFlow()

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
                    // Periksa jika data cart kosong
                    val cartItems = data.data.cartItem ?: emptyList()
                    _cartItemList.value = mapCartToProductDetail(cartItems)
                    _getCartState.value = GetCartState.Success(data)
                },
                onFailure = { error ->
                    _getCartState.value = GetCartState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }

    private fun mapCartToProductDetail(cartItem: List<CartItemData>?): List<ProductDetail> {
        if (cartItem.isNullOrEmpty()) return emptyList()

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


    fun deleteCartItems() {
        viewModelScope.launch {
            _deleteCartState.value = try {
                cartRepository.deleteCartItems()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    fun updateItemCart(productId: String, quantity: Int) {
        viewModelScope.launch {
            val currentCart = _cartItemList.value
            val updatedCart = currentCart.map { item ->
                if (item.id == productId) item.copy(stockCount = quantity) else item
            }
            _cartItemList.value = updatedCart

            // Update total price
            _totalPrice.value = updatedCart.sumOf { it.price.toLong() * it.stockCount.toLong() }

            _itemUpdateStates[productId] = true
            val response = cartRepository.updateCartItem(productId, quantity)
            if (!response.isSuccessful) {
                // Revert state if update fails
                _cartItemList.value = currentCart
                _totalPrice.value = currentCart.sumOf { it.price.toLong() * it.stockCount.toLong() }
            }
            _itemUpdateStates[productId] = false
        }
    }

    private fun generateOrderId(length: Int): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return (1..length)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    fun getIdOrder() {
        _idOrder.value = generateOrderId(10)
    }

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

sealed class DeleteCartState {
    object Idle: DeleteCartState()
    object Loading: DeleteCartState()
    data class Success(val message: CartDeleteResponse): DeleteCartState()
    data class Error(val message: String): DeleteCartState()
}

sealed class UpdateCartState {
    object Idle: UpdateCartState()
    object Loading: UpdateCartState()
    data class Success(val data: UpdateItemResponse): UpdateCartState()
    data class Error(val message: String): UpdateCartState()
}